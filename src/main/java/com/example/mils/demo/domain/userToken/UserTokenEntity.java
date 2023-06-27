package com.example.mils.demo.domain.userToken;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserTokenEntity {
    private long id;
    private String username;
    private String token;
}
