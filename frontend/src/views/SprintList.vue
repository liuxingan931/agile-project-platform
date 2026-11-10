<template>
  <el-card title="迭代 Sprint 管理">
    <el-button type="primary" @click="openAddDialog">新增迭代</el-button>
    <el-table :data="tableData" border style="margin-top:16px">
      <el-table-column prop="id" label="ID" width="70"/>
      <el-table-column prop="sprintName" label="迭代名称"/>
      <el-table-column prop="goal" label="迭代目标"/>
      <el-table-column prop="startTime" label="开始时间" width="120" :formatter="formatDate"/>
      <el-table-column prop="endTime" label="结束时间" width="120" :formatter="formatDate"/>
      <el-table-column prop="statusDesc" label="状态" width="90"/>
      <el-table-column label="操作" width="160">
        <template #default="scope">
          <el-button size="small" @click="openEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDel(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑迭代' : '新增迭代'" width="520px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="迭代名称">
          <el-input v-model="form.sprintName" placeholder="请输入迭代名称"/>
        </el-form-item>
        <el-form-item label="迭代目标">
          <el-input v-model="form.goal" type="textarea" placeholder="请输入迭代目标"/>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker v-model="form.startTime" type="date" placeholder="选择开始日期" style="width:100%"/>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker v-model="form.endTime" type="date" placeholder="选择结束日期" style="width:100%"/>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width:100%">
            <el-option label="规划中" value="PLANNING"/>
            <el-option label="进行中" value="RUNNING"/>
            <el-option label="已完成" value="FINISHED"/>
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
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getSprintByProject, addSprint, updateSprint, delSprint } from '../api/sprint'

const tableData = ref([])
const dialogVisible = ref(false)
const form = ref({})
const isEdit = ref(false)
const projectId = 1

// 表格日期格式化：把后端返回的时间转成 yyyy-MM-dd
const formatDate = (row, column, cellValue) => {
  if (!cellValue) return ''
  const d = new Date(cellValue)
  if (isNaN(d.getTime())) return cellValue
  const p = n => String(n).padStart(2, '0')
  return `${d.getFullYear()}-${p(d.getMonth() + 1)}-${p(d.getDate())}`
}

const load = async () => {
  const res = await getSprintByProject(projectId)
  tableData.value = res.data || []
}

const openAddDialog = () => {
  form.value = {
    projectId,
    status: 'PLANNING',
    sprintName: '',
    goal: '',
    startTime: null,
    endTime: null
  }
  isEdit.value = false
  dialogVisible.value = true
}

const openEdit = (row) => {
  form.value = {
    ...row,
    startTime: row.startTime ? new Date(row.startTime) : null,
    endTime: row.endTime ? new Date(row.endTime) : null
  }
  isEdit.value = true
  dialogVisible.value = true
}

const submit = async () => {
  try {
    if (isEdit.value) {
      await updateSprint(form.value)
      ElMessage.success('修改成功')
    } else {
      await addSprint(form.value)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    load()
  } catch (e) {
    // 失败提示由 request 响应拦截器统一处理
  }
}

const handleDel = async (id) => {
  await delSprint(id)
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>
