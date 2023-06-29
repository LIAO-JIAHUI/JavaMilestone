package com.example.mils.demo.web.user;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class SignupForm {
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @NotBlank
    private String username;
    @Size(max = 64)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @NotBlank
    private String password;
    private String token;
}
