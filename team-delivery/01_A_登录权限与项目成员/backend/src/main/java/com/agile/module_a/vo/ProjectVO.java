package com.agile.module_a.vo;

import lombok.Data;
import java.util.Date;

@Data
public class ProjectVO {
    private Long id;
    private String projectName;
    private String description;
    private Date createTime;
}
