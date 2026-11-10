package com.agile.module_c.mapper;

import com.agile.module_c.entity.UserStory;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserStoryMapper {
    int insert(UserStory story);
    UserStory selectById(Long id);
    List<UserStory> selectBySprintId(@Param("sprintId") Long sprintId);
    int update(UserStory story);
    int deleteById(Long id);
}
