/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.service;

import co.id.mii.mcc63lmsserverside.repository.ContentRepository;
import co.id.mii.mcc63lmsserverside.model.Content;
import co.id.mii.mcc63lmsserverside.model.Dto.ContentDto;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

    public List<Content> getAll() {
        return contentRepository.findAll();
    }
    
    public Content getById(Long id) {
        return contentRepository.findById(id).orElseThrow(() 
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not Found"));
    }
    
    public Content create(ContentDto contentDto) {
        Content content = modelMapper.map(contentDto, Content.class);
        content.setModule(moduleService.getById(contentDto.getModuleId()));
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
}