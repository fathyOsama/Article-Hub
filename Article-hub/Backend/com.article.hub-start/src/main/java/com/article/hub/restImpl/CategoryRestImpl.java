package com.article.hub.restImpl;

import com.article.hub.entity.Category;
import com.article.hub.rest.CategoryRest;
import com.article.hub.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class CategoryRestImpl implements CategoryRest {

    Logger log = LoggerFactory.getLogger(CategoryRestImpl.class);

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseEntity<?> addNewCategory(Category category) {
        try {
            return categoryService.addNewCategory(category);
        } catch (Exception ex) {
            log.error("Exception in addNewCategory : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllCategory() {
        try {
            return categoryService.getAllCategory();
        } catch (Exception ex) {
            log.error("Exception in getAllCategory : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateCategory(Category category) {
        try {
            return categoryService.updateCategory(category);
        } catch (Exception ex) {
            log.error("Exception in updateCategory : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
