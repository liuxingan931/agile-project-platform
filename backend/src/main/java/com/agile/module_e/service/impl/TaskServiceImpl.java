package com.agile.module_e.service.impl;

import com.agile.common.convert.BeanConvertUtil;
import com.agile.common.enums.TaskStatusEnum;
import com.agile.module_e.dto.TaskAddDTO;
import com.agile.module_e.dto.TaskUpdateDTO;
import com.agile.module_e.entity.Task;
import com.agile.module_e.mapper.TaskMapper;
import com.agile.module_e.service.TaskService;
import com.agile.module_e.vo.TaskVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    private TaskMapper taskMapper;

    @Override
    public boolean addTask(TaskAddDTO dto) {
        Task task = BeanConvertUtil.convert(dto, Task.class);
        if(task.getStatus() == null){
            task.setStatus(TaskStatusEnum.TODO.getCode());
        }
        task.setCreateTime(new Date());
        return taskMapper.insert(task) > 0;
    }

    @Override
    public TaskVO getVOById(Long id) {
        Task task = taskMapper.selectById(id);
        if(task == null) return null;
        TaskVO vo = BeanConvertUtil.convert(task, TaskVO.class);
        vo.setStatusDesc(TaskStatusEnum.getDescByCode(task.getStatus()));
        return vo;
    }

    @Override
    public List<TaskVO> listAllVO() {
        return toVOList(taskMapper.selectAll());
    }

    @Override
    public List<TaskVO> listVOBySprintId(Long sprintId) {
        return toVOList(taskMapper.selectBySprintId(sprintId));
    }

    @Override
    public List<TaskVO> listVOByStoryId(Long storyId) {
        return toVOList(taskMapper.selectByStoryId(storyId));
    }

    @Override
    public boolean updateTask(TaskUpdateDTO dto) {
        Task task = BeanConvertUtil.convert(dto, Task.class);
        if(task.getStatus() == null){
            task.setStatus(TaskStatusEnum.TODO.getCode());
        }
        return taskMapper.update(task) > 0;
    }

    @Override
    public boolean deleteTask(Long id) {
        return taskMapper.deleteById(id) > 0;
    }

    private List<TaskVO> toVOList(List<Task> list) {
        List<TaskVO> voList = BeanConvertUtil.convertList(list, TaskVO.class);
        voList.forEach(vo -> vo.setStatusDesc(TaskStatusEnum.getDescByCode(vo.getStatus())));
        return voList;
    }
}
