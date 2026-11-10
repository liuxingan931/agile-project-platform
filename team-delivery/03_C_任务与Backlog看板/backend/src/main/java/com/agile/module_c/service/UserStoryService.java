package com.agile.module_c.service;

import com.agile.module_c.dto.StoryAddDTO;
import com.agile.module_c.dto.StoryUpdateDTO;
import com.agile.module_c.vo.StoryVO;
import java.util.List;

public interface UserStoryService {
    boolean addStory(StoryAddDTO dto);
    StoryVO getVOById(Long id);
    List<StoryVO> listVOBySprintId(Long sprintId);
    boolean updateStory(StoryUpdateDTO dto);
    boolean deleteStory(Long id);
}
