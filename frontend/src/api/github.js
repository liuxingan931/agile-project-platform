import request from '../utils/request'

export function getGitHubRepo(owner, repo, platform = 'auto'){
  return request({url:`/github/repo/${owner}/${repo}?platform=${platform}`,method:'get'})
}
