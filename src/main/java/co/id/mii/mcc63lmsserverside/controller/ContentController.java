/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.controller;

import co.id.mii.mcc63lmsserverside.service.ContentService;
import co.id.mii.mcc63lmsserverside.model.Content;
import co.id.mii.mcc63lmsserverside.model.dto.ContentData;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Agung
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    private ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping
    public ResponseEntity<List<Content>> getAll() {
        return new ResponseEntity(contentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> getById(@PathVariable Long id) {
        return new ResponseEntity(contentService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Content> create(@RequestBody ContentData contentData) {
        return new ResponseEntity(contentService.create(contentData), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Content> update(@PathVariable Long id, @RequestBody Content content) {
        return new ResponseEntity(contentService.update(id, content), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Content> delete(@PathVariable Long id) {
        return new ResponseEntity(contentService.delete(id), HttpStatus.OK);
    }
}