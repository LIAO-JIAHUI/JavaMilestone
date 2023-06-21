package com.example.mils.demo.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserEntity {
    // フィールドの定義
    private long id;
    private String username;
    private String password;
    private Boolean enabled;
}
