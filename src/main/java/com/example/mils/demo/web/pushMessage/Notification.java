package com.example.mils.demo.web.pushMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Notification {
    private String milestoneId; // milestone id
    private String title; // milestone title
    private String type; // create edit delete
    private String to;
    private String from;
}