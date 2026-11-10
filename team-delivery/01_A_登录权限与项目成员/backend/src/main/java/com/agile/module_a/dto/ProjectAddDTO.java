package com.agile.module_a.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class ProjectAddDTO {
    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    private String description;
}
