package com.example.mils.demo.web.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;

import com.example.mils.demo.domain.user.UserGroupEntity;

@AllArgsConstructor
@Data
public class UserGlobalEntity {
    // フィールドの定義
    private String username;
    private String displayName;
    private List<String> roles;
    private Boolean isDark;
    private String iconString;
    private List<UserGroupEntity> groups;
}
