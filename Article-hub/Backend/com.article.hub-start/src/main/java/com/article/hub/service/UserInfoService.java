package com.article.hub.service;

import com.article.hub.entity.AuthRequest;
import com.article.hub.entity.UserInfo;
import org.springframework.http.ResponseEntity;

public interface UserInfoService {

    ResponseEntity<?> addNewAppuser(UserInfo userInfo);

    ResponseEntity<?> login(AuthRequest authRequest);

    ResponseEntity<?> getAllAppuser();

    ResponseEntity<?> updateUserStatus(UserInfo userInfo);

    ResponseEntity<?> checkToken();

    ResponseEntity<?> updateUser(UserInfo userInfo);
}
