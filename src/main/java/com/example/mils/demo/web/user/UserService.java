package com.example.mils.demo.web.user;

import org.springframework.stereotype.Service;

import com.example.mils.demo.domain.user.UserEntity;

import lombok.RequiredArgsConstructor;
// import java.sql.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity find(String userName) {
        return userRepository.find(userName);
    }

    public void changeDarkMode(String userName, boolean isDark) {
        userRepository.changeDarkMode(userName, isDark);
    }

    public void update(String userName, String icon, String displayName) {
        userRepository.update(userName, icon, displayName);
    }
}
