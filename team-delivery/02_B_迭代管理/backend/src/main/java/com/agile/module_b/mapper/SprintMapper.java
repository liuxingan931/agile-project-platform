package com.agile.module_b.mapper;

import com.agile.module_b.entity.Sprint;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SprintMapper {
    int insert(Sprint sprint);
    Sprint selectById(Long id);
    List<Sprint> selectByProjectId(@Param("projectId") Long projectId);
    int update(Sprint sprint);
    int deleteById(Long id);
}
