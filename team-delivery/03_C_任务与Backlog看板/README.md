# 成员 C —— 用户故事 Backlog、任务与看板

## 一、职责范围

1. **用户故事（Backlog）**：故事的新增、查询、修改、删除；维护故事点（`estimate_point`）
2. **任务管理**：任务的新增、查询、修改、删除，支持按迭代、按故事筛选
3. **任务看板**：三列看板（TODO / DOING / DONE），支持「推进」状态
4. **变更记录**：任务、故事的状态变更留痕

## 二、交付文件

C 是四人中模块最多的一位，覆盖两个后端模块 `module_c` 与 `module_e`：

```
03_C_任务与Backlog看板/
├── backend/src/main/java/com/agile/module_c/     # 用户故事
│   ├── controller/UserStoryController.java
│   ├── dto/StoryAddDTO.java, StoryUpdateDTO.java
│   ├── entity/UserStory.java
│   ├── mapper/UserStoryMapper.java
│   ├── service/UserStoryService.java, service/impl/UserStoryServiceImpl.java
│   └── vo/StoryVO.java
├── backend/src/main/java/com/agile/module_e/     # 任务
│   ├── controller/TaskController.java
│   ├── dto/TaskAddDTO.java, TaskUpdateDTO.java
│   ├── entity/Task.java
│   ├── mapper/TaskMapper.java
│   ├── service/TaskService.java, service/impl/TaskServiceImpl.java
│   └── vo/TaskVO.java
├── backend/src/main/resources/mapper/UserStoryMapper.xml
├── backend/src/main/resources/mapper/TaskMapper.xml
├── backend/src/test/java/com/agile/UserStoryServiceTest.java
└── frontend/src/
    ├── views/StoryList.vue      # 用户故事 Backlog 页面
    ├── views/TaskBoard.vue      # 任务看板（三列）
    ├── api/story.js
    └── api/task.js
```

## 三、接口清单

### 用户故事 `/api/story`

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/api/story` | 新增故事 |
| GET | `/api/story/{id}` | 故事详情 |
| GET | `/api/story/sprint/{sid}` | 按迭代查故事 |
| PUT | `/api/story` | 修改故事 |
| DELETE | `/api/story/{id}` | 删除故事 |

### 任务 `/api/task`

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/api/task` | 新增任务 |
| GET | `/api/task/{id}` | 任务详情 |
| GET | `/api/task/list` | 全部任务 |
| GET | `/api/task/sprint/{sid}` | 按迭代查任务 |
| GET | `/api/task/story/{storyId}` | 按故事查任务 |
| PUT | `/api/task` | 修改任务（含状态推进） |
| DELETE | `/api/task/{id}` | 删除任务 |

## 四、数据表

**user_story**：`id / title / content / status / estimate_point / sprint_id / priority / create_time`

**task**：`id / title / description / status / story_id / sprint_id / assignee / estimate_hours / create_time`

## 五、核心设计点

1. **故事点是燃尽图的唯一数据来源**
   `UserStory.estimatePoint` 的求和就是 D 燃尽图的「总故事点」。实测无故事时 D 会退化到兜底值 20，所以演示燃尽图前必须先在这里录入带估算点的故事。

2. **任务状态三态与看板列一一对应**
   `TODO → DOING → DONE`，与 `TaskStatusEnum` 对应，看板三列直接按状态分组渲染，点「推进」就是把状态往后挪一档。

3. **module_e 是后期补齐的模块，注册容易漏**
   新增 module_e 后启动报 `A component required a bean of type 'TaskMapper' that could not be found`，原因是 `AgileApplication` 的 `@MapperScan` 只扫了 a/b/c。补上 `com.agile.module_e.mapper` 才启动成功 —— 新增后端模块务必同步改 `@MapperScan`。

4. **校验与前端对齐**
   `TaskAddDTO` 的必填字段（如 title、status）必须与前端表单控件一致，否则新增会被后端校验拦下。

## 六、前端页面

- `StoryList.vue`：故事 Backlog 表格，含标题、内容、状态、故事点、所属迭代，支持新增/编辑/删除
- `TaskBoard.vue`：三列看板（待办 / 进行中 / 已完成），卡片显示任务标题、负责人、预估工时，可「推进」状态、可删除、可新增

## 七、与其他成员的协作

- 依赖 **B** 的 `sprint_id`：故事与任务都挂在迭代下
- 依赖 **A** 的 `project_id`：项目是顶层聚合
- 向 **D** 提供数据：`estimate_point` 决定燃尽图总点数，任务状态决定剩余工作量

## 八、验收要点

- 故事新增 → 按迭代查询 → 修改 → 删除，全链路 code=200
- 任务新增 → 列表 → 推进到 DOING → 按 id 查 → 删除，全链路 code=200
- 看板三列渲染正确，状态推进后卡片移动到下一列
- 建表：`agile_db` 中 `user_story`、`task` 表存在（`SHOW TABLES` 可验证）
