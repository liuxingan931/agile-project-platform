package com.agile.module_b.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Sprint {
    private Long id;
    private String sprintName;
    private String goal;
    private Date startTime;
    private Date endTime;
    private String status;
    private Long projectId;
    private Date createTime;
}
