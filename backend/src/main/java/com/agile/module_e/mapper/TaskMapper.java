package com.agile.module_e.mapper;

import com.agile.module_e.entity.Task;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface TaskMapper {
    int insert(Task task);
    Task selectById(Long id);
    List<Task> selectAll();
    List<Task> selectBySprintId(@Param("sprintId") Long sprintId);
    List<Task> selectByStoryId(@Param("storyId") Long storyId);
    int update(Task task);
    int deleteById(Long id);
}
