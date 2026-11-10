package com.agile.module_d.vo;

import lombok.Data;

/**
 * 代码仓库信息视图对象，用于前后端展示与代码仓库联动
 */
@Data
public class GitHubRepoVO {
    private String name;
    private String fullName;
    private String description;
    private String language;
    private String defaultBranch;
    private String htmlUrl;
    private String updatedAt;
    private Integer stars;
    private Integer forks;
    private Integer openIssues;
    private Integer watchers;
    /** 数据来源平台：GitHub / Gitee */
    private String source;
}
