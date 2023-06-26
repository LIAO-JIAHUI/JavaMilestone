package com.example.mils.demo.web.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.*;

@AllArgsConstructor
@Data
public class UserGlobalEntity {
    // フィールドの定義
    private String username;
    private List<String> roles;
    private Boolean isDark;
    private String iconString;
}
