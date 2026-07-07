package com.article.hub.rest;

import com.article.hub.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Path;

@RequestMapping(path = "/category")
public interface CategoryRest {

    @PostMapping(path = "/addNewCategory")
    ResponseEntity<?> addNewCategory(@RequestBody(required = true) Category category);

    @GetMapping(path = "/getAllCategory")
    ResponseEntity<?> getAllCategory();

    @PostMapping(path = "/updateCategory")
    ResponseEntity<?> updateCategory(@RequestBody(required = true) Category category);

}
