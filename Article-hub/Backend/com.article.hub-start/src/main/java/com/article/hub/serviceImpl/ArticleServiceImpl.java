package com.article.hub.serviceImpl;

import com.article.hub.dao.ArticleRepository;
import com.article.hub.entity.Article;
import com.article.hub.entity.Category;
import com.article.hub.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class ArticleServiceImpl implements ArticleService {

    Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public ResponseEntity<?> addNewArticle(Article article) {
        try {
            if (!Objects.isNull(article)) {
                String errorKeyValue = article.checkForNullValues();
                if (Objects.isNull(errorKeyValue)) {
                    article.setPublication_date(new Date());
                    article.setCategory(new Category(article.getCategoryId()));
                    articleRepository.save(article);
                    return new ResponseEntity<>("{\"message\":\"Article added successfully\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\":\"Invalid value for ( "+errorKeyValue+" )\"}",HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>("{\"message\":\"Invalid data\"}", HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("Exception in addNewArticle : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllArticle() {
        try {
            return new ResponseEntity<>(articleRepository.getAllArticle(null), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception in getAllArticle : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllPublishedArticle() {
        try {
            return new ResponseEntity<>(articleRepository.getAllArticle("Published"), HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception in getAllPublishedArticle : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateArticle(Article article) {
        try {
         String errorKeyValue = article.checkForNullValues();
         if (Objects.isNull(errorKeyValue) && !Objects.isNull(article.getId())) {
            Integer updateCount = articleRepository.updateArticle(article.getTitle(), article.getContent(), article.getCategoryId(), new Date(), article.getStatus(), article.getId());
            if (updateCount == 0) {
                return new ResponseEntity<>("{\"message\":\"Article id does not found.\"}", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>("{\"message\":\"Article updated successfully.\"}", HttpStatus.OK);
            }
         } else {
             return new ResponseEntity<>("{\"message\":\"Invalid value for ( " + errorKeyValue + ").\"}", HttpStatus.BAD_REQUEST);
         }
        } catch (Exception ex) {
            log.error("Exception in updateArticle : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> deleteArticle(Integer id) {
        try {
            if (!Objects.isNull(id)) {
                Integer deleteCount = articleRepository.deleteArticle(id);
                if (deleteCount == 0) {
                    return new ResponseEntity<>("{\"message\":\"Article id does not found.\"}", HttpStatus.NOT_FOUND);
                } else {
                    return new ResponseEntity<>("{\"message\":\"Article delete successfully.\"}", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("{\"message\":\"Invalid value for ( Article Id )\"}", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            log.error("Exception in deleteArticle : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
