package com.agile;

import com.agile.module_c.dto.StoryAddDTO;
import com.agile.module_c.service.UserStoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;

@SpringBootTest
public class UserStoryServiceTest {
    @Resource
    private UserStoryService storyService;

    @Test
    void testAddStory(){
        StoryAddDTO dto = new StoryAddDTO();
        dto.setContent("作为项目成员，我可以新增用户故事");
        dto.setPriority(1);
        dto.setEstimatePoint(3);
        dto.setSprintId(1L);
        dto.setAssigneeId(1L);
        storyService.addStory(dto);
    }
}
