package com.agile.module_b.service;

import com.agile.module_b.dto.SprintAddDTO;
import com.agile.module_b.dto.SprintUpdateDTO;
import com.agile.module_b.vo.SprintVO;
import java.util.List;

public interface SprintService {
    boolean addSprint(SprintAddDTO dto);
    SprintVO getVOById(Long id);
    List<SprintVO> listVOByProjectId(Long projectId);
    boolean updateSprint(SprintUpdateDTO dto);
    boolean deleteSprint(Long id);
}
