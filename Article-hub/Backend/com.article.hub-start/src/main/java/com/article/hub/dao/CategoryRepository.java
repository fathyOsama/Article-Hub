package com.article.hub.dao;

import com.article.hub.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Boolean existsByNameIgnoreCase(String name);

    @Modifying
    @Transactional
    @Query(
            value = "update category set name=:name where id=:id",
            nativeQuery = true)
    Integer updateCategory(@Param("name") String name, @Param("id") Integer id);

}
