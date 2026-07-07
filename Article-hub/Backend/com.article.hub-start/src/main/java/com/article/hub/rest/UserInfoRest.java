package com.article.hub.rest;

import com.article.hub.entity.AuthRequest;
import com.article.hub.entity.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/appUser")
public interface UserInfoRest {

    @PostMapping(path = "/addNewAppuser")
    ResponseEntity<?> addNewAppuser(@RequestBody(required = true) UserInfo userInfo);

    @PostMapping(path = "/login")
    ResponseEntity<?> login(@RequestBody(required = true) AuthRequest authRequest);

    @GetMapping(path = "/getAllAppuser")
    ResponseEntity<?> getAllAppuser();

    @PostMapping("/updateUserStatus")
    ResponseEntity<?> updateUserStatus(@RequestBody(required = true) UserInfo userInfo);

    @GetMapping("/checkToken")
    ResponseEntity<?> checkToken();

    @PostMapping("/updateUser")
    ResponseEntity<?> updateUser(@RequestBody(required = true) UserInfo userInfo);
}
