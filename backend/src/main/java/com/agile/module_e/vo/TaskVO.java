package com.agile.module_e.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TaskVO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String statusDesc;
    private Long storyId;
    private Long sprintId;
    private String assignee;
    private BigDecimal estimateHours;
    private Date createTime;
}
