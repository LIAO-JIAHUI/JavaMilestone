package com.example.mils.demo.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserEntity {
    private String username;
    private String password;
    private String displayName;
    private Boolean enabled;
    private Boolean isDark;
    private String icon;
}
