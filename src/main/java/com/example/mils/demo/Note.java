package com.example.mils.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Note {
    private String subject;
    private String content;
    private String link;
    // private String image;
}
