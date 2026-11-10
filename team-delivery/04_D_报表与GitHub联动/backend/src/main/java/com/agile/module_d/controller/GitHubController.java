package com.agile.module_d.controller;

import com.agile.common.result.R;
import com.agile.module_d.adapter.GitHubAdapter;
import com.agile.module_d.vo.GitHubRepoVO;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 代码仓库联动接口（对应分工 D：报表与外部系统联动）
 * platform 可选：auto（默认，GitHub失败自动切Gitee）/ github / gitee
 */
@RestController
@RequestMapping("/api/github")
public class GitHubController {
    @Resource
    private GitHubAdapter gitHubAdapter;

    @GetMapping("/repo/{owner}/{repo}")
    public R<GitHubRepoVO> repo(@PathVariable String owner,
                                @PathVariable String repo,
                                @RequestParam(value = "platform", defaultValue = "auto") String platform) {
        try {
            return R.ok(gitHubAdapter.fetchRepo(platform, owner, repo));
        } catch (Exception e) {
            return R.fail("代码仓库接口调用失败：" + e.getMessage());
        }
    }
}
