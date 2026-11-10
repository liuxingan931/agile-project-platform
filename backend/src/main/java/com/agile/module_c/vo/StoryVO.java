package com.agile.module_c.vo;

import lombok.Data;
import java.util.Date;

@Data
public class StoryVO {
    private Long id;
    private String content;
    private Integer priority;
    private Integer estimatePoint;
    private String status;
    private String statusDesc;
    private Long sprintId;
    private Long assigneeId;
    private Date createTime;
}
