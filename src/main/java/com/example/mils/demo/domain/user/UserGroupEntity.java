package com.example.mils.demo.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserGroupEntity {
    private long id;
    private String name;
    private String role;
}
