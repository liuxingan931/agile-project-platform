# 成员 B —— 迭代（Sprint）管理

## 一、职责范围

1. **迭代管理**：Sprint 的新增、按项目查询、详情、修改、删除
2. **迭代状态机**：`PLANNING`（规划中）→ `ACTIVE`（进行中）→ `CLOSED`（已关闭）
3. **时间维度**：维护迭代的起止日期，为 D 的燃尽图提供「天数」依据

## 二、交付文件

```
02_B_迭代管理/
├── backend/src/main/java/com/agile/module_b/
│   ├── controller/SprintController.java      # 迭代接口
│   ├── dto/SprintAddDTO.java                 # 新增参数（含起止时间校验）
│   ├── dto/SprintUpdateDTO.java              # 修改参数
│   ├── entity/Sprint.java                    # 迭代实体
│   ├── mapper/SprintMapper.java
│   ├── service/SprintService.java
│   ├── service/impl/SprintServiceImpl.java
│   └── vo/SprintVO.java
├── backend/src/main/resources/mapper/SprintMapper.xml
├── backend/src/test/java/com/agile/SprintServiceTest.java
└── frontend/src/
    ├── views/SprintList.vue                  # 迭代管理页面
    └── api/sprint.js
```

## 三、接口清单

基础路径 `/api/sprint`。

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/api/sprint` | 新增迭代 |
| GET | `/api/sprint/{id}` | 迭代详情 |
| GET | `/api/sprint/project/{pid}` | 按项目查迭代列表 |
| PUT | `/api/sprint` | 修改迭代 |
| DELETE | `/api/sprint/{id}` | 删除迭代 |

## 四、数据表

**sprint**：`id / sprint_name / goal / start_time / end_time / status / project_id / create_time`

> 注意字段是下划线命名（`sprint_name`、`start_time`、`project_id`），依赖 MyBatis 的 `map-underscore-to-camel-case: true` 才能映射到实体驼峰字段。这条配置缺失会表现为「接口 200 但字段全 null」。

## 五、核心设计点

1. **新增校验与前端表单必须一致**
   `SprintAddDTO` 对 `startTime`、`endTime` 加了 `@NotNull`，前端表单一开始只有名称/目标/状态，导致新增必被校验拦截。修复方式是给 `SprintList.vue` 补两个 `el-date-picker`，表格补开始/结束时间列，编辑时把字符串转 Date 回显。

2. **日期是燃尽图的基础**
   D 的燃尽图用 `resolveDays()` 依据 `start_time`、`end_time` 计算迭代天数（限定 2–60 天）。迭代没有日期时只能退化为默认天数，演示效果会失真，所以 B 的日期必须填全。

3. **按项目隔离**
   迭代挂在 `project_id` 下，同一项目可有多条迭代；前端下拉切换不同迭代即可看到不同的燃尽曲线（实测 Sprint 01 为 15 天、"123" 为 16 天）。

## 六、前端页面

`SprintList.vue`：迭代列表表格（ID / 名称 / 目标 / 状态 / 开始时间 / 结束时间 / 操作），顶部「新增迭代」，表单含名称、目标、状态（下拉）、开始时间、结束时间（日期选择器）。

## 七、与其他成员的协作

- **C**：任务的 `sprint_id` 指向 B 的迭代，任务看板可按迭代过滤
- **D**：燃尽图的 `xAxis` 天数、`sprintName` 全部来自 B 的迭代数据；不传 `sprintId` 时 D 会退化为取项目下第一个迭代

## 八、验收要点

- 新增迭代时时间不填 → 前端表单拦截（`el-form` 必填校验），后端 `@NotNull` 二次兜底
- `GET /api/sprint/project/1` 返回该项目的迭代列表，字段 `sprintName`、`startTime` 有值不为 null
- 修改、删除正常返回 code=200
