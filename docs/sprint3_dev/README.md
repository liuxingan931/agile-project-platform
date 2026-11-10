# Sprint 3｜第三次课：后端持久化开发

## 目标

完成 Sprint、UserStory 的 CRUD 开发，MyBatis 持久化，单元测试，REST接口。

## 模块归属

- module_b：Sprint迭代管理
- module_c：用户故事/Backlog管理

## 产出清单

1. MySQL建表脚本
2. Entity实体类
3. Mapper接口 + XML映射文件
4. Service业务层
5. Controller控制层（统一返回R）
6. 参数校验 + 全局异常处理
7. 单元测试用例
8. API接口文档

## API 接口文档

BaseURL：http://localhost:8080/api

### Sprint 迭代接口

```
POST   /sprint            新增迭代
GET    /sprint/{id}       查询单个迭代
GET    /sprint/project/{pid}  查询项目下全部迭代
PUT    /sprint            修改迭代
DELETE /sprint/{id}       删除迭代
```

### UserStory 用户故事接口

```
POST   /story             新增用户故事
GET    /story/{id}        查询单个用户故事
GET    /story/sprint/{sid} 查询迭代下所有用户故事
PUT    /story             修改用户故事
DELETE /story/{id}        删除用户故事
```

### 统一返回格式

```json
{
  "code": 200,
  "msg": "success",
  "data": {}
}
```
