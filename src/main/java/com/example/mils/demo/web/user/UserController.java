package com.example.mils.demo.web.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.mils.demo.web.usecase.UserAuthUsecase;
import jakarta.servlet.http.*;
import java.io.IOException;

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
        modelAndView.addObject("userForm", new UserForm());

        return modelAndView;
    }

    /**
     * ユーザの登録処理
     * 
     * @param userForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/signup")
    public ModelAndView register(
            @Validated @ModelAttribute UserForm userForm,
            BindingResult bindingResult,
            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("signup");
            modelAndView.addObject("userForm", userForm);
            return modelAndView;
        }
        try {
            userAuthUsecase.userCreate(userForm, request);
        } catch (Exception e) {
            log.error("ユーザ作成 or ログイン失敗", e);
            return new ModelAndView("redirect:/user");
        }

        return new ModelAndView("redirect:/");
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