package com.article.hub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@NamedQuery(name = "Article.getAllArticle",
        query = "select new com.article.hub.entity.Article(id, title, content, status, publication_date, category.id, category.name) from article where (:status is null or status=:status)")

@NamedQuery(name = "Article.updateArticle", query = "update article a SET a.title=:title, a.content=:content, a.category.id =:categoryId, a.publication_date=:publication_date, a.status=:status where a.id=:id")

@NamedQuery(name = "Article.deleteArticle", query = "delete from article where id=:id")

@Entity(name = "article")
@Table(name = "article")
@Data
@AllArgsConstructor
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Date publication_date;

    private String status;

    //-----------------------
    @Transient
    private Integer categoryId;

    @Transient
    private String categoryName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(Date publication_date) {
        this.publication_date = publication_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Article() {
        super();
    }

    public Article(Integer id, String title, String content, String status, Date publication_date, Integer categoryId, String categoryName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.publication_date = publication_date;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String checkForNullValues(){
        if (title == null) {
            return "Title";
        }
        if (content == null) {
            return "Content";
        }
        if (categoryId == null) {
            return "Category";
        }
        if (status == null) {
            return "Status";
        }
        return null;
    }
}
