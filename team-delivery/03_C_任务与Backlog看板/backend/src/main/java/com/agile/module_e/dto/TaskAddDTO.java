package com.agile.module_e.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class TaskAddDTO {
    @NotBlank(message = "任务标题不能为空")
    private String title;

    private String description;

    private String status;

    private Long storyId;

    private Long sprintId;

    private String assignee;

    private BigDecimal estimateHours;
}
