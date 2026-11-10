package com.agile;

import com.agile.module_b.dto.SprintAddDTO;
import com.agile.module_b.service.SprintService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
public class SprintServiceTest {
    @Resource
    private SprintService sprintService;

    @Test
    void testAddSprint(){
        SprintAddDTO dto = new SprintAddDTO();
        dto.setSprintName("Sprint 1");
        dto.setGoal("完成基础迭代功能");
        dto.setStartTime(new Date());
        dto.setEndTime(new Date());
        dto.setProjectId(1L);
        sprintService.addSprint(dto);
    }
}
