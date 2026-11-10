package com.agile.module_b.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class SprintUpdateDTO {
    @NotNull(message = "迭代ID不能为空")
    private Long id;

    private String sprintName;
    private String goal;
    private Date startTime;
    private Date endTime;
    private String status;
}
