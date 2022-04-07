/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import co.id.mii.mcc63lmsserverside.controller.ContentController;
import co.id.mii.mcc63lmsserverside.model.Content;
import co.id.mii.mcc63lmsserverside.model.dto.ContentData;
import co.id.mii.mcc63lmsserverside.repository.ContentRepository;
import co.id.mii.mcc63lmsserverside.util.StorageService;
import net.bytebuddy.utility.RandomString;

/**
 *
 * @author Agung
 */
@Service
public class ContentService {

    private final List<String> contentTypes =
            Arrays.asList("video/mp4", "video/webm", "video/mkv");

    private ContentRepository contentRepository;
    private ModuleService moduleService;
    private ModelMapper modelMapper;

    @Autowired
    public ContentService(ContentRepository contentRepository, ModuleService moduleService,
            ModelMapper modelMapper) {
        this.contentRepository = contentRepository;
        this.moduleService = moduleService;
        this.modelMapper = modelMapper;
    }

    public List<Content> getAll() {
        return contentRepository.findAll()
                .stream()
                .map(c -> new Content(
                        c.getId(),
                        c.getTitle(),
                        c.getDescription(),
                        toUri(c.getVideo()),
                        c.getModule()))
                .collect(Collectors.toList());
    }

    public Content getById(Long id) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Content not Found"));
        content.setVideo(toUri(content.getVideo()));
        return content;
    }

    public Content create(ContentData contentData) {
        String fileContentType = contentData.getFile().getContentType();

        if (contentTypes.contains(fileContentType)) {
            String video = RandomString.make(20) + "."
                    + FilenameUtils.getExtension(contentData.getFile().getOriginalFilename());

            StorageService.store("upload/module", video, contentData.getFile());

            Content content = modelMapper.map(contentData, Content.class);
            content.setVideo(video);
            content.setModule(moduleService.getById(contentData.getModuleId()));
            return contentRepository.save(content);
        } else {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "File types are not allowed.");
        }
    }

    public Content update(Long id, ContentData contentData) {
        String fileContentType = contentData.getFile().getContentType();

        if (contentTypes.contains(fileContentType)) {
            String video = RandomString.make(20) + "."
                    + FilenameUtils.getExtension(contentData.getFile().getOriginalFilename());

            StorageService.store("upload/module", video, contentData.getFile());

            Content content = modelMapper.map(contentData, Content.class);
            content.setId(id);
            content.setVideo(video);
            content.setModule(moduleService.getById(contentData.getModuleId()));
            return contentRepository.save(content);
        } else {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                    "File types are not allowed.");
        }
    }

    public Content delete(Long id) {
        Content content = getById(id);
        contentRepository.delete(content);
        return content;
    }

    private String toUri(String filename) {
        return MvcUriComponentsBuilder
                .fromMethodName(ContentController.class, "getFile", filename)
                .build().toUri().toString();
    }
}
