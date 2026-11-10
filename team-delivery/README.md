# 敏捷研发项目管理平台 —— 四人分工交付包

《软件工程实验》选题 2：敏捷研发项目管理平台

本目录按小组四人的职责，把后端模块、前端页面与文档拆分归集，便于分工评阅与责任追溯。

> **重要说明**：本目录是**评阅用的分组视图**（文件均为副本）。实际运行项目请使用项目根目录下的 `backend/`、`frontend/`，那套结构未做任何改动，Maven 与 Vite 可直接构建运行。

## 一、分工总览

| 成员 | 负责内容 | 后端模块 | 前端页面 | 核心接口 |
|---|---|---|---|---|
| **A** | 登录与权限、项目与成员管理、公共工程 | `module_a` + `common` + `config` | 项目管理 | `/api/project` |
| **B** | 迭代（Sprint）管理 | `module_b` | 迭代管理 | `/api/sprint` |
| **C** | 用户故事 Backlog、任务与看板、变更记录 | `module_c` + `module_e` | 用户故事 Backlog、任务看板 | `/api/story`、`/api/task` |
| **D** | 报表（燃尽图）、快照、GitHub/Gitee 联动 | `module_d` | 燃尽图报表、GitHub 联动 | `/api/report/burndown`、`/api/github/repo/{owner}/{repo}` |

## 二、目录结构

```
team-delivery/
├── 00_公共工程与文档/          # 全体共用的工程骨架、构建配置、数据库脚本、四轮课文档
│   ├── backend/
│   │   ├── pom.xml
│   │   ├── sql/schema_mysql.sql
│   │   └── src/main/
│   │       ├── java/com/agile/
│   │       │   ├── AgileApplication.java   # 启动类，@MapperScan 扫描 a/b/c/e 四个包
│   │       │   ├── common/                 # R 统一返回体、状态枚举、全局异常、Bean 转换
│   │       │   └── config/CorsConfig.java  # 跨域配置
│   │       └── resources/application.yml   # 端口 8080、MySQL 连接、MyBatis 配置
│   ├── frontend/                           # 前端公共骨架
│   │   ├── index.html, package.json, vite.config.js
│   │   └── src/App.vue, main.js, utils/request.js
│   └── docs/                               # 四轮课交付文档
│       ├── sprint1_requirement/README.md   # 第一次课：需求分析与 UML
│       ├── sprint2_design/                 # 第二次课：架构与概要设计（含部署图）
│       │   ├── README.md
│       │   └── plantuml/deployment.puml
│       ├── sprint3_dev/README.md           # 第三次课：后端持久化开发
│       └── sprint4_final/README.md         # 第四次课：前端实现与联调总结
│
├── 01_A_登录权限与项目成员/
│   ├── README.md
│   ├── backend/src/main/java/com/agile/module_a/   # Project/User 实体、DTO、VO、Mapper、Service、Controller
│   ├── backend/src/main/resources/mapper/ProjectMapper.xml
│   └── frontend/src/{views/ProjectList.vue, api/project.js}
│
├── 02_B_迭代管理/
│   ├── README.md
│   ├── backend/src/main/java/com/agile/module_b/
│   ├── backend/src/main/resources/mapper/SprintMapper.xml
│   ├── backend/src/test/java/com/agile/SprintServiceTest.java
│   └── frontend/src/{views/SprintList.vue, api/sprint.js}
│
├── 03_C_任务与Backlog看板/
│   ├── README.md
│   ├── backend/src/main/java/com/agile/module_c/   # UserStory
│   ├── backend/src/main/java/com/agile/module_e/   # Task
│   ├── backend/src/main/resources/mapper/{UserStoryMapper.xml, TaskMapper.xml}
│   ├── backend/src/test/java/com/agile/UserStoryServiceTest.java
│   └── frontend/src/{views/StoryList.vue, views/TaskBoard.vue, api/story.js, api/task.js}
│
└── 04_D_报表与GitHub联动/
    ├── README.md
    ├── backend/src/main/java/com/agile/module_d/   # ReportController、GitHubAdapter、GitHubController
    ├── frontend/src/{views/Burndown.vue, views/GitHubPanel.vue, api/report.js, api/github.js}
    └── docs/data_sync_design.md                    # 外部系统数据同步方案
```

## 三、系统启动顺序

三个组件按依赖关系依次启动：

1. **MySQL**（库 `agile_db`，3306）
   建表脚本：`00_公共工程与文档/backend/sql/schema_mysql.sql`
2. **后端 SpringBoot**（8080）
   `cd backend` → `mvn spring-boot:run`
3. **前端 Vue3 + Vite**（5173）
   `cd frontend` → `npm install`（首次）→ `npm run dev`
   → 浏览器打开 `http://localhost:5173`

## 四、接口一览

| 模块 | 方法 | 路径 | 说明 |
|---|---|---|---|
| A | POST | `/api/project` | 新增项目 |
| A | GET | `/api/project/list` | 项目列表 |
| A | GET | `/api/project/{id}` | 项目详情 |
| A | PUT | `/api/project` | 修改项目 |
| A | DELETE | `/api/project/{id}` | 删除项目 |
| B | POST | `/api/sprint` | 新增迭代 |
| B | GET | `/api/sprint/project/{pid}` | 按项目查迭代 |
| B | GET / PUT / DELETE | `/api/sprint`、`/api/sprint/{id}` | 查改删 |
| C | POST / GET / PUT / DELETE | `/api/story` 系列 | 用户故事 CRUD |
| C | POST / GET / PUT / DELETE | `/api/task` 系列 | 任务 CRUD，另含 `/api/task/sprint/{sid}`、`/api/task/story/{storyId}` |
| D | GET | `/api/report/burndown?sprintId={id}` | 燃尽图数据（按迭代实算） |
| D | GET | `/api/github/repo/{owner}/{repo}?platform=auto\|github\|gitee` | 代码仓库统计，支持双平台降级 |

统一返回体：`{ code, message, data }`，`code=200` 表示成功。

## 五、协作边界

- **A** 提供 `R` 统一返回体、`GlobalExceptionHandler`、`CorsConfig`、`BeanConvertUtil`，以及 `Project`、`User` 实体；B/C/D 的接口全部复用这套约定。
- **B** 的 `Sprint` 是 C 的任务、D 的燃尽图的时间维度来源（燃尽图天数由 Sprint 起止日期算出）。
- **C** 的 `UserStory.estimatePoint` 是 D 燃尽图「总故事点」的数据来源。
- **D** 只读 B/C 的数据，不直接写业务表；外部仓库数据通过适配器拉取，不落库。

## 六、已验证功能

- 项目 CRUD、任务 CRUD（含状态推进）、迭代 CRUD、故事 CRUD 全链路通过
- 燃尽图按迭代真实计算：不同迭代天数不同（实测 15 天 / 16 天），计划线线性递减到 0
- GitHub 联动双平台可用：GitHub 可达时取 GitHub（`vuejs/core` stars 54888），不可达时自动降级 Gitee
- 部署图：`docs/sprint2_design/plantuml/deployment.puml` + README 内 Mermaid 版本
