package com.agile.module_c.service.impl;

import com.agile.common.convert.BeanConvertUtil;
import com.agile.common.enums.StoryStatusEnum;
import com.agile.module_c.dto.StoryAddDTO;
import com.agile.module_c.dto.StoryUpdateDTO;
import com.agile.module_c.entity.UserStory;
import com.agile.module_c.mapper.UserStoryMapper;
import com.agile.module_c.service.UserStoryService;
import com.agile.module_c.vo.StoryVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserStoryServiceImpl implements UserStoryService {
    @Resource
    private UserStoryMapper userStoryMapper;

    @Override
    public boolean addStory(StoryAddDTO dto) {
        UserStory story = BeanConvertUtil.convert(dto, UserStory.class);
        if(story.getStatus() == null){
            story.setStatus(StoryStatusEnum.TODO.getCode());
        }
        if(story.getPriority() == null){
            story.setPriority(1);
        }
        if(story.getEstimatePoint() == null){
            story.setEstimatePoint(1);
        }
        story.setCreateTime(new Date());
        return userStoryMapper.insert(story) > 0;
    }

    @Override
    public StoryVO getVOById(Long id) {
        UserStory story = userStoryMapper.selectById(id);
        StoryVO vo = BeanConvertUtil.convert(story, StoryVO.class);
        vo.setStatusDesc(StoryStatusEnum.getDescByCode(story.getStatus()));
        return vo;
    }

    @Override
    public List<StoryVO> listVOBySprintId(Long sprintId) {
        List<UserStory> list = userStoryMapper.selectBySprintId(sprintId);
        List<StoryVO> voList = BeanConvertUtil.convertList(list, StoryVO.class);
        voList.forEach(vo -> vo.setStatusDesc(StoryStatusEnum.getDescByCode(vo.getStatus())));
        return voList;
    }

    @Override
    public boolean updateStory(StoryUpdateDTO dto) {
        UserStory story = BeanConvertUtil.convert(dto, UserStory.class);
        return userStoryMapper.update(story) > 0;
    }

    @Override
    public boolean deleteStory(Long id) {
        return userStoryMapper.deleteById(id) > 0;
    }
}
