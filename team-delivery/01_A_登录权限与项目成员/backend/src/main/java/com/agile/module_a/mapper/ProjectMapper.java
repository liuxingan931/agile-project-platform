package com.agile.module_a.mapper;

import com.agile.module_a.entity.Project;
import java.util.List;

public interface ProjectMapper {
    int insert(Project project);
    Project selectById(Long id);
    List<Project> selectAll();
    int update(Project project);
    int deleteById(Long id);
}
