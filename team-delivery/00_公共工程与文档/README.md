# 00 公共工程与文档（全组共用）

本目录存放四人共同依赖的工程骨架、构建配置、数据库脚本和四轮课文档，不属于某一个成员单独所有。

## 一、后端公共部分

| 文件 | 作用 |
|---|---|
| `backend/pom.xml` | Maven 构建配置，SpringBoot 2.7.15 + MyBatis + MySQL 驱动 |
| `backend/src/main/java/com/agile/AgileApplication.java` | 启动类，`@MapperScan` 扫描 `module_b / module_c / module_a / module_e` 四个 Mapper 包 |
| `backend/src/main/java/com/agile/common/result/R.java` | 统一返回体 `{code, message, data}`，全组接口共用 |
| `backend/src/main/java/com/agile/common/exception/GlobalExceptionHandler.java` | 全局异常处理，统一转成标准返回体 |
| `backend/src/main/java/com/agile/common/enums/` | `SprintStatusEnum`、`StoryStatusEnum`、`TaskStatusEnum` 状态枚举 |
| `backend/src/main/java/com/agile/common/convert/BeanConvertUtil.java` | Entity / DTO / VO 之间的字段拷贝工具 |
| `backend/src/main/java/com/agile/config/CorsConfig.java` | 跨域配置，允许前端 5173 调用 8080 |
| `backend/src/main/resources/application.yml` | 端口、数据源、MyBatis 配置 |
| `backend/sql/schema_mysql.sql` | 建表脚本（project / sys_user / sprint / user_story / task 五张表） |

### application.yml 关键配置

```yaml
server:
  port: 8080
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/agile_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
mybatis:
  mapper-locations: classpath:mapper/*.xml
  configuration:
    map-underscore-to-camel-case: true   # 必须开启，否则下划线列映射不到驼峰字段
```

> **踩坑记录**：`map-underscore-to-camel-case` 漏配时，接口返回 200 但 `sprint_name`、`start_time`、`project_id` 等字段全为 `null`，是静默失败，不报错。排查这类"接口成功但字段丢失"的问题，先看这条配置。

> **踩坑记录**：新增 module_e（任务）后启动报 `A component required a bean of type 'TaskMapper' that could not be found`，根因是 `@MapperScan` 漏了新包。新增模块时必须同步补 `@MapperScan`。

## 二、前端公共骨架

| 文件 | 作用 |
|---|---|
| `frontend/index.html` | 页面入口，含 meta、全局基础样式、首屏 loading 动画 |
| `frontend/package.json` | 依赖：vue 3.3.4 / element-plus 2.3.8 / axios 1.4.0 / echarts 5.4.3 |
| `frontend/vite.config.js` | 端口 5173，`open: true` |
| `frontend/src/main.js` | 应用入口，注册 Element Plus |
| `frontend/src/App.vue` | 主容器，6 个 Tab：项目管理 / 迭代管理 / 用户故事 Backlog / 任务看板 / 燃尽图报表 / GitHub 联动 |
| `frontend/src/utils/request.js` | axios 封装，baseURL `http://localhost:8080/api` |

> **踩坑记录**：`index.html` 若是 0 字节空文件，Vite 会报 `Could not auto-determine entry point ... html files`，浏览器 404。入口文件必须包含 `#app` 与 `/src/main.js` 引用。

## 三、四轮课文档索引

| 文档 | 对应课程 | 内容 |
|---|---|---|
| `docs/sprint1_requirement/README.md` | 第一次课 | 选题 2 需求分析、核心概念建模（产品 Backlog / 迭代 / 任务）、用例与 UML |
| `docs/sprint2_design/README.md` | 第二次课 | 架构设计（前后端分离）、包图/序列图、**部署图（系统拓扑）** |
| `docs/sprint2_design/plantuml/deployment.puml` | 第二次课 | 部署图 PlantUML 源文件：浏览器 → SpringBoot → MySQL，后端 → GitHub/Gitee API |
| `docs/sprint3_dev/README.md` | 第三次课 | 后端持久化开发：实体、Mapper、Service、Controller 分层 |
| `docs/sprint4_final/README.md` | 第四次课 | 前端实现与联调总结、6 个页面说明、燃尽图数据生成逻辑 |

## 四、系统拓扑

```
浏览器(localhost:5173)
      │ HTTP
      ▼
SpringBoot 后端(localhost:8080)
      ├──▶ MySQL(agile_db, 3306)
      └──▶ GitHub API / Gitee API（集成边界，只读拉取仓库统计）
```

部署图详见 `docs/sprint2_design/plantuml/deployment.puml`，README 中另有 Mermaid 版本便于直接预览。
