<template>
  <el-card title="项目管理">
    <el-button type="primary" @click="openAdd">新增项目</el-button>
    <el-table :data="tableData" border style="margin-top:16px">
      <el-table-column prop="id" label="ID" width="70"/>
      <el-table-column prop="projectName" label="项目名称"/>
      <el-table-column prop="description" label="项目描述"/>
      <el-table-column prop="createTime" label="创建时间" width="160" :formatter="formatDate"/>
      <el-table-column label="操作" width="160">
        <template #default="scope">
          <el-button size="small" @click="openEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDel(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑项目' : '新增项目'" width="500px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="项目名称">
          <el-input v-model="form.projectName" placeholder="请输入项目名称"/>
        </el-form-item>
        <el-form-item label="项目描述">
          <el-input v-model="form.description" type="textarea" placeholder="请输入项目描述"/>
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
import { getProjectList, addProject, updateProject, delProject } from '../api/project'

const tableData = ref([])
const dialogVisible = ref(false)
const form = ref({})
const isEdit = ref(false)

const formatDate = (row, column, cellValue) => {
  if (!cellValue) return ''
  const d = new Date(cellValue)
  if (isNaN(d.getTime())) return cellValue
  const p = n => String(n).padStart(2, '0')
  return `${d.getFullYear()}-${p(d.getMonth() + 1)}-${p(d.getDate())}`
}

const load = async () => {
  const res = await getProjectList()
  tableData.value = res.data || []
}

const openAdd = () => {
  form.value = { projectName: '', description: '' }
  isEdit.value = false
  dialogVisible.value = true
}

const openEdit = (row) => {
  form.value = { ...row }
  isEdit.value = true
  dialogVisible.value = true
}

const submit = async () => {
  try {
    if (isEdit.value) {
      await updateProject(form.value)
      ElMessage.success('修改成功')
    } else {
      await addProject(form.value)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    load()
  } catch (e) {}
}

const handleDel = async (id) => {
  await delProject(id)
  ElMessage.success('删除成功')
  load()
}

onMounted(load)
</script>
