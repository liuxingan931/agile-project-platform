package com.agile.module_c.entity;

import lombok.Data;
import java.util.Date;

@Data
public class UserStory {
    private Long id;
    private String content;
    private Integer priority;
    private Integer estimatePoint;
    private String status;
    private Long sprintId;
    private Long assigneeId;
    private Date createTime;
}
