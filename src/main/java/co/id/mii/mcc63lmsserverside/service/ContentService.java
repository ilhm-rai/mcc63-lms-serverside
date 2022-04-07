/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.service;

import co.id.mii.mcc63lmsserverside.repository.ContentRepository;
import co.id.mii.mcc63lmsserverside.util.StorageService;
import net.bytebuddy.utility.RandomString;
import co.id.mii.mcc63lmsserverside.model.Content;
import co.id.mii.mcc63lmsserverside.model.dto.ContentData;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Agung
 */
@Service
public class ContentService {

    private final List<String> contentTypes =
            Arrays.asList("application/pdf", "video/mp4", "video/webm", "video/mkv");

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
        return contentRepository.findAll();
    }

    public Content getById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Content not Found"));
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
}
