package com.agile.module_a.service.impl;

import com.agile.common.convert.BeanConvertUtil;
import com.agile.module_a.dto.ProjectAddDTO;
import com.agile.module_a.dto.ProjectUpdateDTO;
import com.agile.module_a.entity.Project;
import com.agile.module_a.mapper.ProjectMapper;
import com.agile.module_a.service.ProjectService;
import com.agile.module_a.vo.ProjectVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Resource
    private ProjectMapper projectMapper;

    @Override
    public boolean addProject(ProjectAddDTO dto) {
        Project project = BeanConvertUtil.convert(dto, Project.class);
        project.setCreateTime(new Date());
        return projectMapper.insert(project) > 0;
    }

    @Override
    public ProjectVO getVOById(Long id) {
        return BeanConvertUtil.convert(projectMapper.selectById(id), ProjectVO.class);
    }

    @Override
    public List<ProjectVO> listAllVO() {
        return BeanConvertUtil.convertList(projectMapper.selectAll(), ProjectVO.class);
    }

    @Override
    public boolean updateProject(ProjectUpdateDTO dto) {
        return projectMapper.update(BeanConvertUtil.convert(dto, Project.class)) > 0;
    }

    @Override
    public boolean deleteProject(Long id) {
        return projectMapper.deleteById(id) > 0;
    }
}
