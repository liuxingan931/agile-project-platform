# 敏捷研发项目管理平台

四人小组软件工程实验项目，采用敏捷开发模式，分四次迭代完成。

## 技术栈

- 后端：SpringBoot 2.7 + MyBatis + MySQL 8.x
- 前端：Vue3 + Element Plus + ECharts + Axios
- 架构：前后端分离，模块化单体，Entity/DTO/VO 三层分层

## 小组分工

- A：登录权限、项目管理、成员管理（骨架已完成）
- B：Sprint迭代管理模块（完整实现）
- C：用户故事/Backlog管理模块（完整实现）
- D：报表燃尽图、前端开发、联调整合（完整实现）

## 架构分层说明

- Entity：数据库实体，与表一一对应
- DTO：前端请求入参，附带参数校验
- VO：前端返回视图对象，包含展示字段
- Service：业务逻辑层，对象转换与业务处理
- Controller：接口层，统一返回格式
- Mapper：持久层，MyBatis SQL映射

## 迭代记录

### Sprint 1｜第一次课：需求分析

- 用户故事梳理、用例建模
- 领域类图、用例图 PlantUML
- 小组分工与迭代规划

### Sprint 2｜第二次课：架构设计

- 前后端分离工程骨架搭建
- 模块划分 module_a ~ module_d
- 逻辑包图、部署图、序列图设计

### Sprint 3｜第三次课：后端开发

- MySQL数据库表设计
- Entity/DTO/VO 三层分层
- Sprint、UserStory 模块 CRUD 完整实现
- 参数校验、全局异常处理
- 单元测试、接口文档

### Sprint 4｜第四次课：前端联调&验收

- Vue3前端三页面：迭代管理、Backlog管理、燃尽图
- Axios封装，前后端接口联调
- ECharts燃尽图可视化
- 交叉测试、bug修复、最终实验报告

## 启动方式

1. 执行 `backend/sql/schema_mysql.sql` 初始化数据库
2. 修改 `application.yml` 中数据库账号密码
3. 后端启动：运行 `AgileApplication.java`
4. 前端启动：进入frontend目录，执行 `npm install` → `npm run dev`
5. 访问：http://localhost:5173
