# 成员 A —— 登录权限与项目成员管理

## 一、职责范围

1. **项目管理**：项目的新增、查询、修改、删除（完整 CRUD）
2. **用户与成员**：`sys_user` 用户实体与成员归属（项目维度）
3. **公共工程**：统一返回体 `R`、全局异常处理、跨域配置、Bean 转换工具、启动类与 Mapper 扫描（这部分实体文件见 `../00_公共工程与文档`）

## 二、交付文件

```
01_A_登录权限与项目成员/
├── backend/src/main/java/com/agile/module_a/
│   ├── controller/ProjectController.java      # 项目 CRUD 接口
│   ├── dto/ProjectAddDTO.java                 # 新增参数
│   ├── dto/ProjectUpdateDTO.java              # 修改参数
│   ├── entity/Project.java                    # 项目实体
│   ├── entity/User.java                       # 用户实体
│   ├── mapper/ProjectMapper.java              # MyBatis Mapper 接口
│   ├── service/ProjectService.java            # 业务接口
│   ├── service/impl/ProjectServiceImpl.java   # 业务实现
│   └── vo/ProjectVO.java                      # 返回视图对象
├── backend/src/main/resources/mapper/ProjectMapper.xml
└── frontend/src/
    ├── views/ProjectList.vue                  # 项目管理页面（表格 + 弹窗表单）
    └── api/project.js                         # 项目接口封装
```

## 三、接口清单

基础路径 `/api/project`，统一返回 `{code, message, data}`。

| 方法 | 路径 | 说明 | 请求体 / 参数 |
|---|---|---|---|
| POST | `/api/project` | 新增项目 | `ProjectAddDTO`：项目名、描述等 |
| GET | `/api/project/list` | 项目列表 | 无 |
| GET | `/api/project/{id}` | 项目详情 | 路径参数 id |
| PUT | `/api/project` | 修改项目 | `ProjectUpdateDTO`（含 id） |
| DELETE | `/api/project/{id}` | 删除项目 | 路径参数 id |

## 四、数据表

**project**：`project_id / project_name / description / owner / create_time` 等

**sys_user**：`user_id / username / password / role / project_id` 等

建表语句统一在 `../00_公共工程与文档/backend/sql/schema_mysql.sql`，A 负责 project 与 sys_user 两张表的定义。

## 五、前端页面

`ProjectList.vue`：Element Plus 表格展示项目列表，顶部「新增项目」按钮，每行「编辑 / 删除」操作，表单用弹窗（`el-dialog` + `el-form`）。字段包括项目名称、描述、负责人。

## 六、公共工程贡献

A 同时承担全组的公共基础设施，具体内容见 `../00_公共工程与文档/README.md`：

- `R` 统一返回体 —— 所有成员的接口都返回这个结构
- `GlobalExceptionHandler` —— 异常统一兜底，避免前端拿到 500 裸异常
- `CorsConfig` —— 让 5173 的前端能调 8080 的后端
- `BeanConvertUtil` —— Entity/DTO/VO 字段映射
- `AgileApplication` 的 `@MapperScan` —— **新增模块（如 module_e）必须同步加包名，否则启动失败**

## 七、与其他成员的协作

- B/C/D 的所有业务都挂在 `Project` 下（项目是顶层聚合），`project_id` 是跨模块外键
- 前端 6 个 Tab 的容器 `App.vue` 也归 A 维护（见公共工程）
- 迭代、故事、任务的页面都依赖 A 建好的项目和成员数据才能演示

## 八、验收要点

- 项目新增后列表能立刻查到（实测 code=200）
- 修改、删除、按 id 查单个均可正常返回
- 删除不存在的 id 时返回业务错误码而非 500 裸异常（由 `GlobalExceptionHandler` 兜底）
