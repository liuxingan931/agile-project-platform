<template>
  <div id="app">
    <el-container>
      <el-header style="background:#2c3e50;color:#fff;padding:0 20px;line-height:60px;font-size:20px">
        敏捷研发项目管理平台
      </el-header>
      <el-main>
        <el-tabs v-model="activeTab">
          <el-tab-pane label="项目管理" name="project">
            <ProjectList />
          </el-tab-pane>
          <el-tab-pane label="迭代管理" name="sprint">
            <SprintList />
          </el-tab-pane>
          <el-tab-pane label="用户故事Backlog" name="story">
            <StoryList />
          </el-tab-pane>
          <el-tab-pane label="任务看板" name="task">
            <TaskBoard />
          </el-tab-pane>
          <el-tab-pane label="燃尽图报表" name="burndown">
            <Burndown />
          </el-tab-pane>
          <el-tab-pane label="GitHub联动" name="github">
            <GitHubPanel />
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ProjectList from './views/ProjectList.vue'
import SprintList from './views/SprintList.vue'
import StoryList from './views/StoryList.vue'
import TaskBoard from './views/TaskBoard.vue'
import Burndown from './views/Burndown.vue'
import GitHubPanel from './views/GitHubPanel.vue'

const activeTab = ref('project')
</script>

<style>
* { margin:0; padding:0; box-sizing:border-box; }
#app { font-family: Arial, sans-serif; }
</style>
