package com.article.hub.rest;

import com.article.hub.entity.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "article")
public interface ArticleRest {

    @PostMapping(path = "/addNewArticle")
    ResponseEntity<?> addNewArticle(@RequestBody Article article);

    @GetMapping(path = "/getAllArticle")
    ResponseEntity<?> getAllArticle();

    @GetMapping(path = "/getAllPublishedArticle")
    ResponseEntity<?> getAllPublishedArticle();

    @PostMapping(path = "/updateArticle")
    ResponseEntity<?> updateArticle(@RequestBody Article article);

    @DeleteMapping(path = "/deleteArticle/{id}")
    ResponseEntity<?> deleteArticle(@PathVariable Integer id);

}
