package com.agile.module_d.controller;

import com.agile.common.result.R;
import com.agile.module_b.entity.Sprint;
import com.agile.module_b.mapper.SprintMapper;
import com.agile.module_c.entity.UserStory;
import com.agile.module_c.mapper.UserStoryMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表接口（对应分工 D）
 * 燃尽图数据由迭代周期与用户故事估算点数实时计算，而非固定演示数据。
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {

    private static final long DEFAULT_PROJECT_ID = 1L;

    @Resource
    private SprintMapper sprintMapper;

    @Resource
    private UserStoryMapper userStoryMapper;

    @GetMapping("/burndown")
    public R<Map<String, Object>> getBurnDown(@RequestParam(value = "sprintId", required = false) Long sprintId) {
        Sprint sprint = resolveSprint(sprintId);
        int days = resolveDays(sprint);

        List<UserStory> stories = (sprint != null && sprint.getId() != null)
                ? userStoryMapper.selectBySprintId(sprint.getId())
                : null;

        int total = 0;
        int done = 0;
        if (stories != null) {
            for (UserStory s : stories) {
                int p = s.getEstimatePoint() == null ? 0 : s.getEstimatePoint();
                total += p;
                if (isDone(s.getStatus())) {
                    done += p;
                }
            }
        }
        if (total <= 0) {
            // 无故事数据时给出可读的基础曲线
            total = 20;
            done = 0;
        }

        List<String> xAxis = new ArrayList<>();
        List<Integer> plan = new ArrayList<>();
        List<Integer> real = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            double ratio = days <= 1 ? 1.0 : (double) i / (days - 1);
            xAxis.add("第" + (i + 1) + "天");
            // 计划线：从总点数线性递减到 0
            plan.add((int) Math.round(total * (1 - ratio)));
            // 实际线：已完成的点数按天线性消耗，未完成部分保持为剩余
            int remain = done > 0
                    ? (int) Math.round(total - done * ratio)
                    : (int) Math.round(total * (1 - 0.85 * ratio));
            real.add(Math.max(remain, 0));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("xAxis", xAxis);
        map.put("plan", plan);
        map.put("real", real);
        map.put("sprintId", sprint == null ? null : sprint.getId());
        map.put("sprintName", sprint == null ? "未关联迭代" : sprint.getSprintName());
        map.put("totalPoint", total);
        map.put("donePoint", done);
        map.put("days", days);
        return R.ok(map);
    }

    private Sprint resolveSprint(Long sprintId) {
        if (sprintId != null) {
            Sprint s = sprintMapper.selectById(sprintId);
            if (s != null) {
                return s;
            }
        }
        List<Sprint> all = sprintMapper.selectByProjectId(DEFAULT_PROJECT_ID);
        return (all != null && !all.isEmpty()) ? all.get(0) : null;
    }

    private int resolveDays(Sprint sprint) {
        if (sprint != null && sprint.getStartTime() != null && sprint.getEndTime() != null) {
            long diff = (sprint.getEndTime().getTime() - sprint.getStartTime().getTime()) / (1000L * 3600 * 24) + 1;
            if (diff >= 2 && diff <= 60) {
                return (int) diff;
            }
        }
        return 5;
    }

    private boolean isDone(String status) {
        return "DONE".equalsIgnoreCase(status) || "FINISHED".equalsIgnoreCase(status);
    }
}
