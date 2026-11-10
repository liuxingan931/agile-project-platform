package com.agile.module_c.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StoryAddDTO {
    @NotBlank(message = "故事内容不能为空")
    private String content;

    private Integer priority;
    private Integer estimatePoint;

    @NotNull(message = "所属迭代不能为空")
    private Long sprintId;

    private Long assigneeId;
}
