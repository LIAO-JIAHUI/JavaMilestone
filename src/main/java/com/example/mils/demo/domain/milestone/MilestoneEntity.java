package com.example.mils.demo.domain.milestone;

import java.sql.*;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MilestoneEntity {
    // フィールドの定義
    private long id;
    private String author;
    private String title;
    private String description;
    private String status;
    private Date scheduleAt;
    private Date deadlineAt;
    private Timestamp createdAt;
}
