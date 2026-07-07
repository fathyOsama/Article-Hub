package com.article.hub.dao;

import com.article.hub.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> getAllArticle(@Param("status") String status);

    @Modifying
    @Transactional
    Integer updateArticle(@Param("title") String title, @Param("content") String content,
                          @Param("categoryId") Integer categoryId, @Param("publication_date") Date publication_date,
                          @Param("status") String status, @Param("id") Integer id);

    @Modifying
    @Transactional
    Integer deleteArticle(@Param("id") Integer id);

}
