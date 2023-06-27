package com.example.mils.demo.web.user;

import java.util.List;

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

    public List<String> getGroupListByUserName(String userName) {
        return userRepository.getGroupListByUserName(userName);
    }

    public List<String> getUserListByGroup(String group) {
        List<String> groupList = null;
        groupList = userRepository.getUserListByGroup(group);
        return groupList;
    }
}
