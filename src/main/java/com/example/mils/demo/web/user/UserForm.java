package com.example.mils.demo.web.user;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class UserForm {
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @NotNull
    private String username;
    @Size(max = 64)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @NotNull
    private String password;
}
