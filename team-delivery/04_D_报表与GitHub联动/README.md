# 成员 D —— 报表（燃尽图）与 GitHub/Gitee 联动

## 一、职责范围

1. **燃尽图报表**：按迭代真实计算剩余故事点曲线（计划线 + 实际线）
2. **项目快照**：迭代快照与数据留存
3. **外部代码仓库联动**：拉取 GitHub / Gitee 仓库统计（stars、forks、语言），设计与外部系统的数据同步方案
4. **报表接口**：为前端提供图表数据

## 二、交付文件

```
04_D_报表与GitHub联动/
├── backend/src/main/java/com/agile/module_d/
│   ├── controller/ReportController.java     # 燃尽图接口，按 sprintId 实算
│   ├── controller/GitHubController.java     # 仓库统计接口，支持 platform 参数
│   ├── adapter/GitHubAdapter.java           # 双平台适配 + 自动降级
│   └── vo/GitHubRepoVO.java                 # 仓库视图对象（含 source 来源字段）
├── frontend/src/
│   ├── views/Burndown.vue                   # 燃尽图页面
│   ├── views/GitHubPanel.vue                # GitHub 联动页面
│   ├── api/report.js
│   └── api/github.js
└── docs/data_sync_design.md                 # 外部系统数据同步设计
```

## 三、接口清单

### 燃尽图

`GET /api/report/burndown?sprintId={id}`（`sprintId` 可省略）

返回：

```json
{
  "xAxis": ["第1天", "第2天", ...],
  "plan":  [20, 19, 17, ...],
  "real":  [20, 19, 18, ...],
  "sprintId": 2,
  "sprintName": "Sprint 01",
  "totalPoint": 20,
  "donePoint": 0,
  "days": 15
}
```

### 仓库统计

`GET /api/github/repo/{owner}/{repo}?platform=auto|github|gitee`（默认 `auto`）

返回 `R<GitHubRepoVO>`，含 `stars`、`forks`、`language`、`source`（标识数据实际来源是 GitHub 还是 Gitee）。

## 四、核心设计点

### 1. 燃尽图：从「写死演示数据」改为「按迭代实算」

原先的实现是硬编码 5 天假数据（固定 `plan=15,12,9,6,3`、`real=15,13,10,7,4`），无论传什么参数曲线都一样。重写后的算法：

| 步骤 | 方法 | 逻辑 |
|---|---|---|
| 定位迭代 | `resolveSprint(sprintId)` | 传入则按 id 取；不传则取 `projectId=1` 下第一个迭代 |
| 计算天数 | `resolveDays()` | 用 Sprint 的 `start_time`、`end_time` 算出天数，限定 2–60 天 |
| 总点数 | 求和 | 该迭代下所有 `UserStory.estimatePoint` 之和（无数据时兜底 20） |
| 已完成 | 统计 | 状态为 `DONE` / `FINISHED` 的故事点之和 |
| 计划线 | 线性递减 | 总点数按 `ratio` 从满值线性降到 0 |
| 实际线 | 消耗 | 按已完成点数逐步消耗，未完成部分保持剩余 |

实测：`sprintId=2` → "Sprint 01"、**15 天**、`plan=20,19,17,16,14,13,11,10,9,7,6,4,3,1,0`；`sprintId=3` → "123"、**16 天**。不同迭代天数不同，说明参数真实生效。

### 2. GitHub 联动：双平台适配 + 自动降级

设计动机：GitHub API 在国内网络下经常不可达（实测曾整段 `api.github.com` 无法连接），但功能必须能演示。

实现方式：`platform` 三态

- `github`：只请求 GitHub
- `gitee`：只请求 Gitee（`https://gitee.com/api/v5/repos/{owner}/{repo}`）
- `auto`（默认）：先请求 GitHub，异常时**自动降级**到 Gitee

`GitHubRepoVO` 增加 `source` 字段，前端据此显示「来源：GitHub / Gitee」标签，不掩盖真实来源。

实测：`vuejs/core` 在 GitHub 可达时 stars=54888、来源 GitHub；网络异常时同样请求自动落到 Gitee 并返回 code=200。Gitee 分支用 `mindspore/mindspore` 验证（stars 9075 / forks 5283 / Python）。

### 3. 前端图表：三个必须处理的细节

- **统计卡**：迭代名称、总故事点、已完成点数、剩余点数，切换迭代即时重算
- **视觉层次**：计划线为灰色虚线 + 浅填充，实际线为蓝色实线 + 渐变面积 + 最低点 `markPoint`，tooltip 用十字准星
- **容器宽度陷阱**：Element Plus 的 6 个 Tab 默认全部渲染但非当前页隐藏（宽度 0）。若在挂载时就 `echarts.init`，图表会按 0 宽度布局，切到该 Tab 后整张图挤在左侧、x 轴只显示「第1天」。解决方式是加 `ensureChart()`（宽度为 0 时不初始化）+ `ResizeObserver` 监听容器，容器一显示就自动 init/resize，`onBeforeUnmount` 里 `disconnect()` 防止泄漏。

## 五、数据同步方案

技术方案与字段映射见 `docs/data_sync_design.md`，要点：

- 采用 **Pull（拉模式）**：后端定时/按需拉取外部仓库统计，不在外部系统写数据
- 字段映射表：GitHub / Gitee 的 `stargazers_count`、`forks_count`、`language` 统一映射到 `GitHubRepoVO`
- 可扩展点：README 已说明如何接入 GitLab（改 `adapter` 的 base url 与字段解析即可）
- 集成边界：后端只读外部 API，失败自动降级，不影响本地业务

## 六、与其他成员的协作

- 依赖 **B** 的迭代日期（算天数）与迭代名
- 依赖 **C** 的故事点（算总点数）与故事状态（算已完成）
- 不写业务表，纯读 + 外部拉取

## 七、验收要点

- `GET /api/report/burndown?sprintId=2` 返回 15 个数据点，`plan` 单调递减到 0
- 切换 `sprintId=3` 返回 16 个点，曲线随之变化（证明非硬编码）
- `GET /api/github/repo/vuejs/core?platform=auto` 返回 code=200，`source` 字段标明来源
- 切到「燃尽图报表」Tab 后曲线铺满整个卡片宽度，x 轴完整显示所有天数（不是挤在左侧）
- 网络不可达时 auto 模式降级到 Gitee，接口仍返回 200，页面不报错
