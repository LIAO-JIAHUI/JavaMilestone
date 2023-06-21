package com.example.mils.demo.web.milestone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MilestoneForm {
    @NotBlank
    @Size(max=256)
    private String title;
    
    @NotBlank
    @Size(max=256)
    private String description;
}
