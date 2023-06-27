package com.example.mils.demo.domain.milestone;

import java.sql.*;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MilestoneEntity {
    // フィールドの定義
    private long id;
    private String author;
    private long group_id;
    private String title;
    private String description;
    private String status;
    private Date scheduleAt;
    private Date deadlineAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
