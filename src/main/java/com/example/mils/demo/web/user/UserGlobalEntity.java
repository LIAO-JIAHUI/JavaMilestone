package com.example.mils.demo.web.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Data
public class UserGlobalEntity {
    // フィールドの定義
    private String username;
    private Collection<? extends GrantedAuthority> roles;
    private Boolean isDark;
}
