<template>
  <el-card title="代码仓库联动（GitHub / Gitee）">
    <el-form :inline="true" :model="query">
      <el-form-item label="平台">
        <el-select v-model="query.platform" style="width:120px">
          <el-option label="自动" value="auto"/>
          <el-option label="Gitee" value="gitee"/>
          <el-option label="GitHub" value="github"/>
        </el-select>
      </el-form-item>
      <el-form-item label="Owner">
        <el-input v-model="query.owner" placeholder="如 mindspore"/>
      </el-form-item>
      <el-form-item label="仓库名">
        <el-input v-model="query.repo" placeholder="如 mindspore"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :loading="loading" @click="search">查询仓库</el-button>
      </el-form-item>
    </el-form>

    <el-card v-if="repo" shadow="hover" style="margin-top:16px">
      <h3>
        {{ repo.fullName }}
        <el-tag size="small" type="success" style="margin-left:8px">来源：{{ repo.source }}</el-tag>
      </h3>
      <p style="color:#606266;margin:8px 0">{{ repo.description || '（无描述）' }}</p>
      <el-row :gutter="16">
        <el-col :span="6"><div class="stat"><b>{{ repo.stars }}</b><span>Stars</span></div></el-col>
        <el-col :span="6"><div class="stat"><b>{{ repo.forks }}</b><span>Forks</span></div></el-col>
        <el-col :span="6"><div class="stat"><b>{{ repo.openIssues }}</b><span>Open Issues</span></div></el-col>
        <el-col :span="6"><div class="stat"><b>{{ repo.language || '-' }}</b><span>Language</span></div></el-col>
      </el-row>
      <p style="margin-top:12px;font-size:13px;color:#909399">
        默认分支：{{ repo.defaultBranch }} ｜ 更新时间：{{ repo.updatedAt }}
      </p>
      <el-link :href="repo.htmlUrl" target="_blank" type="primary">在 {{ repo.source }} 打开 →</el-link>
    </el-card>

    <div v-else style="color:#c0c4cc;margin-top:20px">
      输入 Owner / 仓库名查询仓库信息，例如 Owner: mindspore，仓库名: mindspore（Gitee）
    </div>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { getGitHubRepo } from '../api/github'

const query = ref({ platform: 'auto', owner: '', repo: '' })
const repo = ref(null)
const loading = ref(false)

const search = async () => {
  if (!query.value.owner || !query.value.repo) {
    ElMessage.warning('请输入 Owner 和仓库名')
    return
  }
  loading.value = true
  try {
    const res = await getGitHubRepo(query.value.owner, query.value.repo, query.value.platform)
    repo.value = res.data
  } catch (e) {
    repo.value = null
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.stat { text-align: center; background: #f5f7fa; border-radius: 6px; padding: 12px; }
.stat b { display: block; font-size: 20px; color: #303133; }
.stat span { font-size: 12px; color: #909399; }
</style>
