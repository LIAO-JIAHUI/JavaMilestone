package com.example.mils.demo.web.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.mils.demo.web.user.UserAuthRepository;
import com.example.mils.demo.domain.userToken.UserTokenRepository;
import com.example.mils.demo.domain.user.UserGroupRepository;
import com.example.mils.demo.web.user.SignupForm;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class UserAuthUsecase {
    private final UserAuthRepository authRepository;
    private final UserTokenRepository userTokenRepository;
    private final UserGroupRepository userGroupRepository;

    /**
     * userCreate
     * ユーザーを作成してDBに登録
     * signupからpost
     * 
     * @param form
     * @param request
     * @throws ServletException
     */
    public void userCreate(SignupForm form, HttpServletRequest request) throws ServletException {
        authRepository.createUser(
                form.getUsername(),
                form.getPassword());
        userGroupRepository.insert(form.getUsername(), 3);
        if (!form.getToken().isEmpty()) {
            userTokenRepository.insert(form.getUsername(), form.getToken());
        }
        request.login(form.getUsername(), form.getPassword());
    }
}
