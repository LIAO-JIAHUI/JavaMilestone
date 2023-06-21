package com.example.mils.demo.web.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.mils.demo.web.user.UserAuthRepository;
import com.example.mils.demo.web.user.UserForm;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserAuthUsecase {
    private final UserAuthRepository authRepository;

    public void userCreate(UserForm form, HttpServletRequest request) throws ServletException {
        authRepository.createUser(
                form.getUsername(),
                form.getPassword());

        request.login(form.getUsername(), form.getPassword());
    }
}
