package com.example.mils.demo.web.user;

import lombok.Data;
import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

// import jakarta.validation.constraints.*;

@AllArgsConstructor
@Data
public class ProfileForm {
    private String username;
    private MultipartFile file;
    private String displayName;
}
