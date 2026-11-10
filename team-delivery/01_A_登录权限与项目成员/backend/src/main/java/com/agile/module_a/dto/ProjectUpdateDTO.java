package com.agile.module_a.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class ProjectUpdateDTO {
    @NotNull(message = "项目ID不能为空")
    private Long id;

    private String projectName;

    private String description;
}
