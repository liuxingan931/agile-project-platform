<template>
  <el-card title="任务看板">
    <div style="margin-bottom:16px">
      <el-button type="primary" @click="openAdd">新增任务</el-button>
      <el-button @click="load">刷新</el-button>
    </div>

    <div class="board">
      <div class="col" v-for="col in columns" :key="col.status">
        <div class="col-header" :style="{ background: col.color }">
          {{ col.label }}（{{ grouped[col.status].length }}）
        </div>
        <div class="col-body">
          <el-card v-for="t in grouped[col.status]" :key="t.id" class="task-card" shadow="hover">
            <div class="task-title">{{ t.title }}</div>
            <div class="task-desc" v-if="t.description">{{ t.description }}</div>
            <div class="task-meta">
              <span>负责人：{{ t.assignee || '未分配' }}</span>
              <span>预估：{{ t.estimateHours || 0 }}h</span>
            </div>
            <div class="task-ops">
              <el-button size="small" v-if="col.status !== 'DONE'" @click="moveNext(t)">推进</el-button>
              <el-button size="small" type="danger" @click="del(t.id)">删除</el-button>
            </div>
          </el-card>
          <div v-if="grouped[col.status].length === 0" class="empty">暂无任务</div>
        </div>
      </div>
    </div>

    <el-dialog v-model="dialogVisible" title="新增任务" width="520px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="任务标题">
          <el-input v-model="form.title" placeholder="请输入任务标题"/>
        </el-form-item>
        <el-form-item label="任务描述">
          <el-input v-model="form.description" type="textarea" placeholder="请输入任务描述"/>
        </el-form-item>
        <el-form-item label="负责人">
          <el-input v-model="form.assignee" placeholder="请输入负责人"/>
        </el-form-item>
        <el-form-item label="预估工时">
          <el-input-number v-model="form.estimateHours" :min="0" :step="0.5"/>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width:100%">
            <el-option label="待办" value="TODO"/>
            <el-option label="进行中" value="DOING"/>
            <el-option label="已完成" value="DONE"/>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="submit">提交</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getTaskList, addTask, updateTask, delTask } from '../api/task'

const columns = [
  { status: 'TODO', label: '待办', color: '#909399' },
  { status: 'DOING', label: '进行中', color: '#409eff' },
  { status: 'DONE', label: '已完成', color: '#67c23a' }
]

const list = ref([])
const dialogVisible = ref(false)
const form = ref({})

const grouped = computed(() => {
  const g = { TODO: [], DOING: [], DONE: [] }
  list.value.forEach(t => {
    if (g[t.status]) {
      g[t.status].push(t)
    } else {
      g.TODO.push(t)
    }
  })
  return g
})

const load = async () => {
  const res = await getTaskList()
  list.value = res.data || []
}

const openAdd = () => {
  form.value = { title: '', description: '', assignee: '', estimateHours: 1, status: 'TODO' }
  dialogVisible.value = true
}

const submit = async () => {
  try {
    await addTask(form.value)
    ElMessage.success('新增成功')
    dialogVisible.value = false
    load()
  } catch (e) {}
}

const del = async (id) => {
  await delTask(id)
  ElMessage.success('删除成功')
  load()
}

const nextStatus = (s) => (s === 'TODO' ? 'DOING' : 'DONE')

const moveNext = async (t) => {
  await updateTask({ ...t, status: nextStatus(t.status) })
  ElMessage.success('状态已推进')
  load()
}

onMounted(load)
</script>

<style scoped>
.board { display: flex; gap: 16px; }
.col { flex: 1; background: #f5f7fa; border-radius: 6px; overflow: hidden; min-height: 300px; }
.col-header { color: #fff; padding: 10px 14px; font-weight: bold; }
.col-body { padding: 10px; display: flex; flex-direction: column; gap: 10px; }
.task-title { font-weight: bold; margin-bottom: 6px; }
.task-desc { color: #606266; font-size: 13px; margin-bottom: 8px; }
.task-meta { display: flex; justify-content: space-between; font-size: 12px; color: #909399; margin-bottom: 8px; }
.task-ops { display: flex; gap: 8px; }
.empty { color: #c0c4cc; text-align: center; padding: 20px 0; font-size: 13px; }
</style>
