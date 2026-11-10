# Sprint 4｜第四次课：前端实现 & 前后端联调 & 项目验收

## 迭代目标

1. 基于Vue3+ElementPlus开发前端页面：迭代管理、用户故事管理、燃尽图报表
2. Axios封装请求，对接后端REST接口，完成CRUD联调
3. ECharts实现燃尽图可视化报表
4. 小组测试、bug修复、仓库整理、撰写最终实验报告

## 前端页面（6 个功能 Tab）

| 页面组件 | 功能 | 说明 |
|---|---|---|
| ProjectList.vue | 项目管理 | 项目增删改查 |
| SprintList.vue | 迭代管理 | Sprint 增删改查（含起止时间） |
| StoryList.vue | 用户故事 Backlog | 按迭代维护用户故事 |
| TaskBoard.vue | 任务看板 | 待办/进行中/已完成三列，可推进状态 |
| Burndown.vue | 燃尽图报表 | ECharts 展示计划线/实际线 |
| GitHubPanel.vue | 代码仓库联动 | 查询 GitHub/Gitee 仓库并展示统计 |

## 燃尽图数据生成逻辑

接口：`GET /api/report/burndown?sprintId={id}`，返回 `xAxis`（日期轴）、`plan`（计划剩余工时）、`real`（实际剩余工时）。

- 总工时：取该迭代下所有用户故事的估算点数之和；
- 计划线：从总工时按迭代天数线性递减至 0；
- 实际线：按每日已完成任务对应的剩余工时累计，得到实际剩余曲线；
- 前端用 ECharts 把两条曲线画在同一坐标系，横轴为迭代第 N 天。

## 前后端联调记录

后端地址：http://localhost:8080
前端地址：http://localhost:5173

### 已调通接口

1. 迭代新增/查询/修改/删除
2. 用户故事新增/查询/修改/删除
3. 燃尽图报表接口

### 遇到的问题与解决

1. 跨域：后端增加跨域配置 CorsConfig
2. 前端数据回显：对象浅拷贝问题
3. 状态枚举前后端保持一致（TODO/DOING/DONE、PLANNING/RUNNING/FINISHED）

## 最终实验报告

本阶段在 `docs/sprint4_final` 目录编写项目完整实验报告。
