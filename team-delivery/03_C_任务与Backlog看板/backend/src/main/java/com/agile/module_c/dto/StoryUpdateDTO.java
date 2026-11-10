package com.agile.module_c.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class StoryUpdateDTO {
    @NotNull(message = "故事ID不能为空")
    private Long id;

    private String content;
    private Integer priority;
    private Integer estimatePoint;
    private String status;
    private Long sprintId;
    private Long assigneeId;
}
