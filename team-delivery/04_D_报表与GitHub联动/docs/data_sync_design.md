# 与外部代码仓库（GitHub / GitLab）的数据同步方案

## 1. 同步目标与范围

敏捷平台需要把代码仓库的元信息纳入项目视图，作为"研发进度—代码活动"联动的依据。当前同步的字段如下（两类字段一一映射，便于双平台统一）：

| 平台字段（GitHub / Gitee） | 平台 VO 字段 | 说明 |
|---|---|---|
| name | name | 仓库名 |
| full_name | fullName | 全名 owner/repo |
| description | description | 仓库描述 |
| language | language | 主语言 |
| default_branch | defaultBranch | 默认分支 |
| html_url | htmlUrl | 仓库主页 |
| updated_at | updatedAt | 最近更新时间 |
| stargazers_count | stars | Star 数 |
| forks_count | forks | Fork 数 |
| open_issues_count | openIssues | 未关闭 Issue 数 |
| watchers_count | watchers | 关注数 |

## 2. 同步方式：拉模式（Pull）

采用**由平台主动拉取**（Pull）的方式，而非仓库反向推送：后端定时或按需调用外部开放 API，拉取仓库元信息并映射为内部 VO。理由：

- 平台方无需向外部系统暴露接口，集成边界清晰；
- 外部 API 只读、无需在仓库侧配置 Webhook；
- 失败可重试、可降级，不影响主业务。

## 3. 集成边界与实现

- 边界类：`com.agile.module_d.adapter.GitHubAdapter`，所有外部访问都收敛在此，Controller / 前端不直接接触外部系统。
- 接口：`GET /api/github/repo/{owner}/{repo}?platform=auto|github|gitee`
- 返回：统一 `R<GitHubRepoVO>`，`source` 字段标识数据来源平台。

## 4. 双平台适配与降级（关键设计）

GitHub 与 Gitee 的开放 API 字段结构高度一致，适配器做统一映射：

- `platform=github`：请求 `https://api.github.com/repos/{owner}/{repo}`
- `platform=gitee`：请求 `https://gitee.com/api/v5/repos/{owner}/{repo}`
- `platform=auto`（默认）：先请求 GitHub，**异常时自动降级**到 Gitee

这样在 GitHub 网络不可达的校园/内网环境下，系统仍能真实演示"仓库联动"，保证功能可用性与答辩稳定性。

## 5. 后续可扩展点

1. 定时同步：用 `@Scheduled` 每日拉取一次仓库指标，落库为快照表，供趋势图使用；
2. 同步到迭代：把 open issue 数、最近提交时间与 Sprint 进度关联，生成"代码活跃度"指标；
3. 鉴权增强：读取私有仓库时增加 Token（Header 授权），Token 存配置中心不落代码；
4. 失败告警：连续失败记录日志并降级缓存上次结果。

## 6. 与 GitLab 的兼容

GitLab 开放 API 路径为 `https://gitlab.com/api/v4/projects/{owner}%2F{repo}`，字段（`star_count`、`forks_count`、`open_issues_count`、`default_branch`、`web_url`）与本方案映射表一一对应，只需在适配器中新增一个 `fetchGitlab()` 分支即可，无需改动上层。
