package com.article.hub.dao;

import com.article.hub.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserInfoRepository  extends JpaRepository<UserInfo, Integer> {

    // select * from appuser where email='abc@malinator.com'
    Optional<UserInfo> findByEmail(String email);

    @Query(name = "UserInfo.getAllAppuser")
    List<UserInfo> getAllAppuser(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(name = "UserInfo.updateUserStatus")
    int updateUserStatus(@Param("status") String status, @Param("id") int id);
}
