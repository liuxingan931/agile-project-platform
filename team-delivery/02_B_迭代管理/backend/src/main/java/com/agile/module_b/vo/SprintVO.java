package com.agile.module_b.vo;

import lombok.Data;
import java.util.Date;

@Data
public class SprintVO {
    private Long id;
    private String sprintName;
    private String goal;
    private Date startTime;
    private Date endTime;
    private String status;
    private String statusDesc;
    private Long projectId;
    private Date createTime;
}
