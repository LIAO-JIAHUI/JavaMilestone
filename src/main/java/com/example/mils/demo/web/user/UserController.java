package com.example.mils.demo.web.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.example.mils.demo.domain.user.UserEntity;
import com.example.mils.demo.web.usecase.UserAuthUsecase;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import javax.sql.rowset.serial.*;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserAuthUsecase userAuthUsecase;
    private final UserService userService;

    /**
     * 登録ページの表示
     * 
     * @return
     */
    @GetMapping("/signup")
    public ModelAndView signup(ModelAndView modelAndView) {
        modelAndView.setViewName("signup");
        modelAndView.addObject("SignupForm", new SignupForm());

        return modelAndView;
    }

    /**
     * ユーザの登録処理
     * 
     * @param SignupForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/signup")
    public ModelAndView register(
            @Validated @ModelAttribute SignupForm SignupForm,
            BindingResult bindingResult,
            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("signup");
            modelAndView.addObject("SignupForm", SignupForm);
            return modelAndView;
        }
        try {
            userAuthUsecase.userCreate(SignupForm, request);
        } catch (Exception e) { // TODO: alter
            log.error("ユーザ作成 or ログイン失敗", e);
            return new ModelAndView("redirect:/user");
        }

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/profile")
    public String show(Model model, @ModelAttribute ProfileForm profileForm) {
        UserGlobalEntity userGlobalEntity = (UserGlobalEntity) model.getAttribute("userHash");
        UserEntity userEntity = userService.find(userGlobalEntity.getUsername());
        profileForm.setUsername(userEntity.getUsername());
        profileForm.setFile(null);
        profileForm.setDisplayName(userEntity.getDisplayName());
        return "user/profile";
    }

    @PostMapping("/upload")
    public String upload(@Validated ProfileForm profileForm, BindingResult bindingResult, Model model)
            throws IOException, SerialException, SQLException {
        byte[] fileBytes = profileForm.getFile().getBytes();
        String imageAsString = Base64.getEncoder().encodeToString(fileBytes);
        userService.update(
                profileForm.getUsername(),
                imageAsString,
                "displayname");
        return "redirect:/user/profile";
    }

    @PatchMapping("/darkmode")
    public void toggleDarkmode(Model model, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        UserGlobalEntity userEntity = (UserGlobalEntity) model.getAttribute("userHash");
        if (userEntity != null) {
            userService.changeDarkMode(userEntity.getUsername(), !userEntity.getIsDark());
        }
        String referer = request.getHeader("referer");
        response.sendRedirect(referer);
    }

}