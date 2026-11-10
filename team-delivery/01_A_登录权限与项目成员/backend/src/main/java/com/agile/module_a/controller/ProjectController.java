package com.agile.module_a.controller;

import com.agile.common.result.R;
import com.agile.module_a.dto.ProjectAddDTO;
import com.agile.module_a.dto.ProjectUpdateDTO;
import com.agile.module_a.service.ProjectService;
import com.agile.module_a.vo.ProjectVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 项目管理接口（对应分工 A：项目与成员）
 */
@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Resource
    private ProjectService projectService;

    @PostMapping
    public R<String> add(@RequestBody @Valid ProjectAddDTO dto){
        projectService.addProject(dto);
        return R.ok("新增项目成功");
    }

    @GetMapping("/{id}")
    public R<ProjectVO> getOne(@PathVariable Long id){
        return R.ok(projectService.getVOById(id));
    }

    @GetMapping("/list")
    public R<List<ProjectVO>> listAll(){
        return R.ok(projectService.listAllVO());
    }

    @PutMapping
    public R<String> update(@RequestBody @Valid ProjectUpdateDTO dto){
        projectService.updateProject(dto);
        return R.ok("修改项目成功");
    }

    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id){
        projectService.deleteProject(id);
        return R.ok("删除项目成功");
    }
}
