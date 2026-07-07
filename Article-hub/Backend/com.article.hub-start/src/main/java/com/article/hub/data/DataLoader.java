package com.article.hub.data;

import com.article.hub.dao.UserInfoRepository;
import com.article.hub.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!userInfoRepository.findByEmail("admin@email.com").isPresent()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setName("admin");
            userInfo.setEmail("admin@email.com");
            userInfo.setIsDeletable("false");
            userInfo.setStatus("ACTIVE");
            userInfo.setPassword(encoder.encode("admin"));
            userInfoRepository.save(userInfo);
        }
        System.out.println("=============== You can login with : email (admin@email.com) and password (admin) ===========");
    }
}
