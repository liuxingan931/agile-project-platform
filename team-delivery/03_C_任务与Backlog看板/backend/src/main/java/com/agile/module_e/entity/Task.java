package com.agile.module_e.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务实体：对应 task 表，是用户故事的进一步拆分，支撑敏捷看板
 */
@Data
public class Task {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long storyId;
    private Long sprintId;
    private String assignee;
    private BigDecimal estimateHours;
    private Date createTime;
}
