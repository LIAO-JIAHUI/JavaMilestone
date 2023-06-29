package com.example.mils.demo.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.mils.demo.domain.user.UserEntity;
import com.example.mils.demo.domain.user.UserGroupEntity;
import com.example.mils.demo.web.user.UserGlobalEntity;
import com.example.mils.demo.web.user.UserService;

import lombok.AllArgsConstructor;

import java.util.*;

import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@ControllerAdvice
@AllArgsConstructor
public class GlobalControllerAdvice {
    private final UserService userService;

    @ModelAttribute("userHash")
    public UserGlobalEntity getCurrentUsername() {
        UserGlobalEntity userGlobalEntity = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                Collection<? extends GrantedAuthority> roles = authentication.getAuthorities();
                List<String> roleStrings = new ArrayList<>();
                for (var role : roles) {
                    roleStrings.add(role.getAuthority());
                }
                UserEntity user = userService.find(username);

                List<UserGroupEntity> groupList = userService.getGroupListByUserName(username);
                userGlobalEntity = new UserGlobalEntity(username, user.getDisplayName(), roleStrings, user.getIsDark(),
                        user.getIcon(), groupList);
            }
        }
        return userGlobalEntity;
    }
}