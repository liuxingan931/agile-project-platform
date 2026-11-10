package com.agile.module_e.service;

import com.agile.module_e.dto.TaskAddDTO;
import com.agile.module_e.dto.TaskUpdateDTO;
import com.agile.module_e.vo.TaskVO;

import java.util.List;

public interface TaskService {
    boolean addTask(TaskAddDTO dto);
    TaskVO getVOById(Long id);
    List<TaskVO> listAllVO();
    List<TaskVO> listVOBySprintId(Long sprintId);
    List<TaskVO> listVOByStoryId(Long storyId);
    boolean updateTask(TaskUpdateDTO dto);
    boolean deleteTask(Long id);
}
