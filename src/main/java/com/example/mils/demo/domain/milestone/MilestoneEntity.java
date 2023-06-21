package com.example.mils.demo.domain.milestone;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MilestoneEntity {
    // フィールドの定義
    private long id;
    private String title;
    private String description;
}
