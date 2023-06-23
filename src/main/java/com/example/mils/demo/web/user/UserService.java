package com.example.mils.demo.web.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean getDarkMode(String userName) {
        return userRepository.getDarkMode(userName);
    }

    public void changeDarkMode(String userName, boolean isDark) {
        userRepository.changeDarkMode(userName, isDark);
    }
}
