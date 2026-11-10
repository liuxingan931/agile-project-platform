package com.agile.module_a.service;

import com.agile.module_a.dto.ProjectAddDTO;
import com.agile.module_a.dto.ProjectUpdateDTO;
import com.agile.module_a.vo.ProjectVO;

import java.util.List;

public interface ProjectService {
    boolean addProject(ProjectAddDTO dto);
    ProjectVO getVOById(Long id);
    List<ProjectVO> listAllVO();
    boolean updateProject(ProjectUpdateDTO dto);
    boolean deleteProject(Long id);
}
