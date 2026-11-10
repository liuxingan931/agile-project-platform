package com.agile.module_e.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TaskUpdateDTO {
    @NotNull(message = "任务ID不能为空")
    private Long id;

    private String title;

    private String description;

    private String status;

    private Long storyId;

    private Long sprintId;

    private String assignee;

    private BigDecimal estimateHours;
}
