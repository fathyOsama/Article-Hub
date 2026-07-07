package com.article.hub.serviceImpl;

import com.article.hub.dao.UserInfoRepository;
import com.article.hub.entity.AuthRequest;
import com.article.hub.entity.UserInfo;
import com.article.hub.filter.JwtAuthFilter;
import com.article.hub.jwtService.JwtService;
import com.article.hub.jwtService.UserInfoDetails;
import com.article.hub.restImpl.UserInfoRestImpl;
import com.article.hub.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    Logger log = LoggerFactory.getLogger(UserInfoRestImpl.class);

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtAuthFilter jwtAuthFilter;


    @Override
    public ResponseEntity<?> addNewAppuser(UserInfo userInfo) {
        try {
            if (!validateUserInfo(userInfo))
                return new ResponseEntity<>("{\"message\":\"Missing Required Data.\"}", HttpStatus.BAD_REQUEST);
            Optional<UserInfo> db = userInfoRepository.findByEmail(userInfo.getEmail());
            if (db.isPresent())
                return new ResponseEntity<>("{\"message\":\"Email Already Exist.\"}", HttpStatus.BAD_REQUEST);
            userInfo.setPassword(encoder.encode(userInfo.getPassword()));
            userInfo.setIsDeletable("true");
            userInfo.setStatus("ACTIVE");
            userInfo.setEmail(userInfo.getEmail().toLowerCase());
            userInfoRepository.save(userInfo);
            return new ResponseEntity<>("{\"message\":\"SignUp Successful.\"}", HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception in addNewUser : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateUserInfo(UserInfo userInfo) {
        return !Objects.isNull(userInfo) && StringUtils.hasText(userInfo.getName())
                && StringUtils.hasText(userInfo.getEmail()) && StringUtils.hasText(userInfo.getPassword());
    }

    @Override
    public ResponseEntity<?> login(AuthRequest authRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail().toLowerCase(),
                    authRequest.getPassword()));
            if (authentication != null && authentication.isAuthenticated()) {
                UserInfoDetails userInfoDetails = (UserInfoDetails) authentication.getPrincipal();
                if ("ACTIVE".equalsIgnoreCase(userInfoDetails.getStatus())) {
                    return new ResponseEntity<>("{\"token\":\"" + jwtService.generateToken(authRequest.getEmail().toLowerCase()) + "\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("{\"message\":\"Wait for admin approval.\"}", HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new UsernameNotFoundException("Invalid user request !");
            }
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>("{\"message\":\"Invalid credentials.\"}", HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Exception in login : {}", ex);
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllAppuser() {
        try {
            String email = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();
            return new ResponseEntity<>(
                    userInfoRepository.getAllAppuser(email), HttpStatus.OK
            );
        } catch (Exception ex) {
            log.error("Exception in getAllAppuser : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);

//        try {
//            return new ResponseEntity<>(userInfoRepository.getAllAppuser(jwtAuthFilter.getEmail()), HttpStatus.OK);
//        } catch (Exception ex) {
//            log.error("Exception in getAllAppuser : {}", ex.getMessage());
//        }
//        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateUserStatus(UserInfo userInfo) {
        try {
            if (!Objects.isNull(userInfo) && !Objects.isNull(userInfo.getId()) && !Objects.isNull(userInfo.getStatus())) {
                Integer updateCount = userInfoRepository.updateUserStatus(userInfo.getStatus(), userInfo.getId());
                if (updateCount == 0) {
                    return new ResponseEntity<>("{\"message\":\"User id doesn't exist.\"}", HttpStatus.BAD_REQUEST);
                } else {
                    return new ResponseEntity<>("{\"message\":\"User update successfully.\"}", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("{\"message\":\"Invalid data.\"}", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            log.error("Exception in updateUserStatus : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> checkToken() {
        return new ResponseEntity<>("{\"message\":\"true.\"}", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateUser(UserInfo userInfo) {
        try {
            Optional<UserInfo> optionalUserInfo = userInfoRepository.findById(userInfo.getId());
            if (!optionalUserInfo.isPresent()) {
                return new ResponseEntity<>("{\"message\":\"User not found.\"}", HttpStatus.BAD_REQUEST);
            }
            UserInfo userInfo1 = optionalUserInfo.get();
            userInfo1.setEmail(userInfo.getEmail());
            userInfo1.setName(userInfo.getName());
            userInfoRepository.save(userInfo1);
            return new ResponseEntity<>("{\"message\":\"User updated successfully.\"}", HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Exception in updateUser : {}", ex.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
