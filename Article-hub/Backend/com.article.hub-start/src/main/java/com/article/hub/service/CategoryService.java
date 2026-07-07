package com.article.hub.service;

import com.article.hub.entity.Category;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<?> addNewCategory(Category category);

    ResponseEntity<?> getAllCategory();

    ResponseEntity<?> updateCategory(Category category);
}
