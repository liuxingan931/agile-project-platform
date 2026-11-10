package com.agile.module_b.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SprintAddDTO {
    @NotBlank(message = "迭代名称不能为空")
    private String sprintName;

    private String goal;

    @NotNull(message = "开始时间不能为空")
    private Date startTime;

    @NotNull(message = "结束时间不能为空")
    private Date endTime;

    @NotNull(message = "所属项目不能为空")
    private Long projectId;
}
