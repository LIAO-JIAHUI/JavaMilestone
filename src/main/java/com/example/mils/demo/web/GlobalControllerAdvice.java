package com.example.mils.demo.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
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
                Boolean isDark = userService.getDarkMode(username);
                userGlobalEntity = new UserGlobalEntity(username, roles, isDark);
            }
        }
        return userGlobalEntity;
    }

    // @ModelAttribute("roles")
    // public Collection<? extends GrantedAuthority> getCurrentUserRole() {
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();
    // if (authentication != null && authentication.isAuthenticated()) {
    // Collection<? extends GrantedAuthority> roles =
    // authentication.getAuthorities();
    // return roles;
    // }
    // return null;
    // }

}