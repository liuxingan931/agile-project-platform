package com.agile.module_b.controller;

import com.agile.common.result.R;
import com.agile.module_b.dto.SprintAddDTO;
import com.agile.module_b.dto.SprintUpdateDTO;
import com.agile.module_b.service.SprintService;
import com.agile.module_b.vo.SprintVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sprint")
public class SprintController {
    @Resource
    private SprintService sprintService;

    @PostMapping
    public R<String> add(@RequestBody @Valid SprintAddDTO dto){
        sprintService.addSprint(dto);
        return R.ok("新增迭代成功");
    }

    @GetMapping("/{id}")
    public R<SprintVO> getOne(@PathVariable Long id){
        return R.ok(sprintService.getVOById(id));
    }

    @GetMapping("/project/{pid}")
    public R<List<SprintVO>> listByProject(@PathVariable Long pid){
        return R.ok(sprintService.listVOByProjectId(pid));
    }

    @PutMapping
    public R<String> update(@RequestBody @Valid SprintUpdateDTO dto){
        sprintService.updateSprint(dto);
        return R.ok("修改迭代成功");
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id){
        sprintService.deleteSprint(id);
        return R.ok("删除迭代成功");
    }
}
