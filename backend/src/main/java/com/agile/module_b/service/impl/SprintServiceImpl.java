package com.agile.module_b.service.impl;

import com.agile.common.convert.BeanConvertUtil;
import com.agile.common.enums.SprintStatusEnum;
import com.agile.module_b.dto.SprintAddDTO;
import com.agile.module_b.dto.SprintUpdateDTO;
import com.agile.module_b.entity.Sprint;
import com.agile.module_b.mapper.SprintMapper;
import com.agile.module_b.service.SprintService;
import com.agile.module_b.vo.SprintVO;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SprintServiceImpl implements SprintService {
    @Resource
    private SprintMapper sprintMapper;

    @Override
    public boolean addSprint(SprintAddDTO dto) {
        Sprint sprint = BeanConvertUtil.convert(dto, Sprint.class);
        sprint.setStatus(SprintStatusEnum.PLANNING.getCode());
        sprint.setCreateTime(new Date());
        return sprintMapper.insert(sprint) > 0;
    }

    @Override
    public SprintVO getVOById(Long id) {
        Sprint sprint = sprintMapper.selectById(id);
        SprintVO vo = BeanConvertUtil.convert(sprint, SprintVO.class);
        vo.setStatusDesc(SprintStatusEnum.getDescByCode(sprint.getStatus()));
        return vo;
    }

    @Override
    public List<SprintVO> listVOByProjectId(Long projectId) {
        List<Sprint> list = sprintMapper.selectByProjectId(projectId);
        List<SprintVO> voList = BeanConvertUtil.convertList(list, SprintVO.class);
        voList.forEach(vo -> vo.setStatusDesc(SprintStatusEnum.getDescByCode(vo.getStatus())));
        return voList;
    }

    @Override
    public boolean updateSprint(SprintUpdateDTO dto) {
        Sprint sprint = BeanConvertUtil.convert(dto, Sprint.class);
        return sprintMapper.update(sprint) > 0;
    }

    @Override
    public boolean deleteSprint(Long id) {
        return sprintMapper.deleteById(id) > 0;
    }
}
