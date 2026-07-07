package com.article.hub.restImpl;

import com.article.hub.entity.AuthRequest;
import com.article.hub.entity.UserInfo;
import com.article.hub.rest.UserInfoRest;
import com.article.hub.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserInfoRestImpl implements UserInfoRest {

    Logger log = LoggerFactory.getLogger(UserInfoRestImpl.class);

    @Autowired
    UserInfoService userInfoService;

    @Override
    public ResponseEntity<?> addNewAppuser(UserInfo userInfo) {
        try {
            return userInfoService.addNewAppuser(userInfo);
        } catch (Exception ex) {
            log.error("Exception in AddNewUser : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> login(AuthRequest authRequest) {
        try {
            return userInfoService.login(authRequest);
        } catch (Exception ex) {
            log.error("Exception in login : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllAppuser() {
        try {
            return userInfoService.getAllAppuser();
        } catch (Exception ex) {
            log.error("Exception in getAllAppuser : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateUserStatus(UserInfo userInfo) {
        try {
            return userInfoService.updateUserStatus(userInfo);
        } catch (Exception ex) {
            log.error("Exception in updateUserStatus : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> checkToken() {
        return userInfoService.checkToken();
    }

    @Override
    public ResponseEntity<?> updateUser(UserInfo userInfo) {
        try {
            return userInfoService.updateUser(userInfo);
        } catch (Exception ex) {
            log.error("Exception in updateUser : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
