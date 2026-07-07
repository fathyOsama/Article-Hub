package com.article.hub.serviceImpl;

import com.article.hub.dao.CategoryRepository;
import com.article.hub.entity.Category;
import com.article.hub.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CategoryServiceImpl implements CategoryService {

    Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> addNewCategory(Category category) {
        try{
            if(!Objects.isNull(category)
                    && Objects.isNull(category.getId())
                    && !Objects.isNull(category.getName())) {

                if (!categoryRepository.existsByNameIgnoreCase(category.getName())) {
                    categoryRepository.save(category);
                    return new ResponseEntity<>("{\"message\":\"Category added successfully.\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\":\"Category allready exist.\"}", HttpStatus.CONFLICT);
                }
            }else {
                return new ResponseEntity<>("{\"message\":\"Invalid data.\"}", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex){
            log.error("Exception in addNewCategory : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }



    @Override
    public ResponseEntity<?> getAllCategory() {
        try {
            return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
        } catch (Exception ex){
            log.error("Exception in addNewCategory : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateCategory(Category category) {
        try {
            if(Objects.nonNull(category)
                    && Objects.nonNull(category.getId())
                    && Objects.nonNull(category.getName())
                    && !category.getName().trim().isEmpty()) {

                if (!categoryRepository.existsByNameIgnoreCase(category.getName())) {
                    Integer updateCount = categoryRepository.updateCategory(
                            category.getName(), category.getId()
                    );
                    if (updateCount == 0) {
                        return new ResponseEntity<>(
                                "{\"message\":\"Category id does not found.\"}",
                                HttpStatus.NOT_FOUND
                        );
                    } else {
                        return new ResponseEntity<>(
                                "{\"message\":\"Category updated successfully.\"}",
                                HttpStatus.OK
                        );
                    }
                } else {
                    return new ResponseEntity<>(
                            "{\"message\":\"Category with name (" + category.getName() + ") already exist.\"}",
                            HttpStatus.CONFLICT
                    );
                }
            }
            return new ResponseEntity<>(
                    "{\"message\":\"Invalid data.\"}",
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception ex) {
            log.error("Exception in updateCategory : {}", ex);
        }
        return new ResponseEntity<>(
                "{\"message\":\"Something went wrong.\"}",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
