/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.service;

import co.id.mii.mcc63lmsserverside.repository.ContentRepository;
import co.id.mii.mcc63lmsserverside.model.Content;
import co.id.mii.mcc63lmsserverside.model.dto.ContentData;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Agung
 */
@Service
public class ContentService {

    private ContentRepository contentRepository;
    private ModuleService moduleService;
    private ModelMapper modelMapper;

    @Autowired
    public ContentService(ContentRepository contentRepository, ModuleService moduleService, ModelMapper modelMapper) {
        this.contentRepository = contentRepository;
        this.moduleService = moduleService;
        this.modelMapper = modelMapper;
    }
    
    @Value("${file.upload-dir}")
    private Path fileStorageLocation;

    public List<Content> getAll() {
        return contentRepository.findAll();
    }

    public Content getById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not Found"));
    }
    
    public Content create(ContentData contentData) throws IOException {
//        File video = new File(url + contentData.getFile().getOriginalFilename());
//        contentData.getFile().transferTo(video);
        
        String filename = StringUtils.cleanPath(Objects.requireNonNull(contentData.getFile().getOriginalFilename()));
        
        filename = filename.substring(0, filename.lastIndexOf("."))
                    .replace(".", "") + "." + filename.substring(filename.lastIndexOf(".") + 1);
        
        Path targetLocation = fileStorageLocation.resolve(filename);
        Files.copy(contentData.getFile().getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        
        Content content = modelMapper.map(contentData, Content.class);
        content.setVideoUrl(targetLocation.toString());
        content.setModule(moduleService.getById(contentData.getModuleId()));
        return contentRepository.save(content);
    }

    public Content update(Long id, Content content) {
        Content c = getById(id);
        content.setId(id);
        content.setModule(c.getModule());
        return contentRepository.save(content);
    }

    public Content delete(Long id) {
        Content content = getById(id);
        contentRepository.delete(content);
        return content;
    }
    
    public Resource getFile(String filename) {
        try {
            Path file = fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not retrieve file " + filename, e);
        }
    }
}
