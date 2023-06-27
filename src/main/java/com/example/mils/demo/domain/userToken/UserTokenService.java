package com.example.mils.demo.domain.userToken;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserTokenService {
    private final UserTokenRepository userTokenRepository;

    public List<UserTokenEntity> findAll() {
        return userTokenRepository.findAll();
    }
}
