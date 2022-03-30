/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.content;

import java.util.List;
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

    @Autowired
    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    public List<Content> getAll() {
        return contentRepository.findAll();
    }
    
    public Content getById(Long id) {
        return contentRepository.findById(id).orElseThrow(() 
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not Found"));
    }
    
    public Content create(Content content) {
        return contentRepository.save(content);
    }
    
    public Content update(Long id, Content content) {
        getById(id);
        content.setId(id);
        return contentRepository.save(content);
    }
    
    public Content delete(Long id) {
        Content content = getById(id);
        contentRepository.delete(content);
        return content;
    }    
}