package com.agile.module_e.controller;

import com.agile.common.result.R;
import com.agile.module_e.dto.TaskAddDTO;
import com.agile.module_e.dto.TaskUpdateDTO;
import com.agile.module_e.service.TaskService;
import com.agile.module_e.vo.TaskVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 任务管理接口（任务看板）
 */
@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Resource
    private TaskService taskService;

    @PostMapping
    public R<String> add(@RequestBody @Valid TaskAddDTO dto){
        taskService.addTask(dto);
        return R.ok("新增任务成功");
    }

    @GetMapping("/{id}")
    public R<TaskVO> getOne(@PathVariable Long id){
        return R.ok(taskService.getVOById(id));
    }

    @GetMapping("/list")
    public R<List<TaskVO>> listAll(){
        return R.ok(taskService.listAllVO());
    }

    @GetMapping("/sprint/{sid}")
    public R<List<TaskVO>> listBySprint(@PathVariable Long sid){
        return R.ok(taskService.listVOBySprintId(sid));
    }

    @GetMapping("/story/{storyId}")
    public R<List<TaskVO>> listByStory(@PathVariable Long storyId){
        return R.ok(taskService.listVOByStoryId(storyId));
    }

    @PutMapping
    public R<String> update(@RequestBody @Valid TaskUpdateDTO dto){
        taskService.updateTask(dto);
        return R.ok("修改任务成功");
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id){
        taskService.deleteTask(id);
        return R.ok("删除任务成功");
    }
}
