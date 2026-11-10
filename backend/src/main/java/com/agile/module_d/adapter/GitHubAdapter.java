package com.agile.module_d.adapter;

import com.agile.module_d.vo.GitHubRepoVO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 代码仓库适配器：支持 GitHub / Gitee 两种外部代码托管平台，
 * 作为敏捷平台与代码仓库联动的数据同步入口（外部系统集成边界在本类）。
 * auto 模式下优先 GitHub，失败自动切换 Gitee，以适配国内网络环境。
 */
@Component
public class GitHubAdapter {

    private final RestTemplate restTemplate = new RestTemplate();

    public GitHubRepoVO fetchRepo(String platform, String owner, String repo) {
        if ("gitee".equalsIgnoreCase(platform)) {
            return fetchGitee(owner, repo);
        }
        if ("github".equalsIgnoreCase(platform)) {
            return fetchGithub(owner, repo);
        }
        // auto：先 GitHub，失败再 Gitee
        try {
            return fetchGithub(owner, repo);
        } catch (Exception e) {
            return fetchGitee(owner, repo);
        }
    }

    @SuppressWarnings("unchecked")
    private GitHubRepoVO fetchGithub(String owner, String repo) {
        String url = "https://api.github.com/repos/" + owner + "/" + repo;
        Map<String, Object> body = request(url, "application/vnd.github+json");
        GitHubRepoVO vo = toVO(body);
        vo.setSource("GitHub");
        return vo;
    }

    @SuppressWarnings("unchecked")
    private GitHubRepoVO fetchGitee(String owner, String repo) {
        String url = "https://gitee.com/api/v5/repos/" + owner + "/" + repo;
        Map<String, Object> body = request(url, "application/json");
        GitHubRepoVO vo = toVO(body);
        vo.setSource("Gitee");
        return vo;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> request(String url, String accept) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", accept);
        headers.set("User-Agent", "agile-platform");
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> resp = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return resp.getBody();
    }

    private GitHubRepoVO toVO(Map<String, Object> body) {
        GitHubRepoVO vo = new GitHubRepoVO();
        if (body == null) return vo;
        vo.setName(str(body.get("name")));
        vo.setFullName(str(body.get("full_name")));
        vo.setDescription(str(body.get("description")));
        vo.setLanguage(str(body.get("language")));
        vo.setDefaultBranch(str(body.get("default_branch")));
        vo.setHtmlUrl(str(body.get("html_url")));
        vo.setUpdatedAt(str(body.get("updated_at")));
        vo.setStars(num(body.get("stargazers_count")));
        vo.setForks(num(body.get("forks_count")));
        vo.setOpenIssues(num(body.get("open_issues_count")));
        vo.setWatchers(num(body.get("watchers_count")));
        return vo;
    }

    private String str(Object o) {
        return o == null ? null : o.toString();
    }

    private Integer num(Object o) {
        return o instanceof Number ? ((Number) o).intValue() : null;
    }
}
