package com.example.mils.demo.domain.milestone;

import java.sql.*;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MilestoneEntity {
    // milestoneテーブル
    // フィールドの定義
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String author;
    private long group_id;
    private String group; // groups.name
    private String title;
    private String description;
    private String status;
    private Date scheduleAt;
    private Date deadlineAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
