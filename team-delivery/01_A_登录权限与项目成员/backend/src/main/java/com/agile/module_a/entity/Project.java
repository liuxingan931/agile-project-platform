package com.agile.module_a.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Project {
    private Long id;
    private String projectName;
    private String description;
    private Date createTime;
}
