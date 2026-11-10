package com.agile.module_c.controller;

import com.agile.common.result.R;
import com.agile.module_c.dto.StoryAddDTO;
import com.agile.module_c.dto.StoryUpdateDTO;
import com.agile.module_c.service.UserStoryService;
import com.agile.module_c.vo.StoryVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/story")
public class UserStoryController {
    @Resource
    private UserStoryService userStoryService;

    @PostMapping
    public R<String> add(@RequestBody @Valid StoryAddDTO dto){
        userStoryService.addStory(dto);
        return R.ok("新增用户故事成功");
    }

    @GetMapping("/{id}")
    public R<StoryVO> getOne(@PathVariable Long id){
        return R.ok(userStoryService.getVOById(id));
    }

    @GetMapping("/sprint/{sid}")
    public R<List<StoryVO>> listBySprint(@PathVariable Long sid){
        return R.ok(userStoryService.listVOBySprintId(sid));
    }

    @PutMapping
    public R<String> update(@RequestBody @Valid StoryUpdateDTO dto){
        userStoryService.updateStory(dto);
        return R.ok("修改用户故事成功");
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id){
        userStoryService.deleteStory(id);
        return R.ok("删除用户故事成功");
    }
}
