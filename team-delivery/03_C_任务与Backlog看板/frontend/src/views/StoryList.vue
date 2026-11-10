<template>
  <el-card title="用户故事 Backlog">
    <el-button type="primary" @click="openAdd">新增用户故事</el-button>
    <el-table :data="tableData" border style="margin-top:16px">
      <el-table-column prop="id" label="ID" width="80"/>
      <el-table-column prop="content" label="故事内容"/>
      <el-table-column prop="priority" label="优先级" width="90"/>
      <el-table-column prop="estimatePoint" label="故事点" width="90"/>
      <el-table-column prop="statusDesc" label="状态" width="100"/>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="openEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDel(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" title="用户故事表单" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea"/>
        </el-form-item>
        <el-form-item label="优先级">
          <el-input-number v-model="form.priority" :min="1"/>
        </el-form-item>
        <el-form-item label="预估故事点">
          <el-input-number v-model="form.estimatePoint" :min="1"/>
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
import { ref,onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getStoryBySprint,addStory,updateStory,delStory } from '../api/story'

const tableData = ref([])
const dialogVisible = ref(false)
const form = ref({})
const isEdit = ref(false)
const sprintId = 1

const load = async ()=>{
  const res = await getStoryBySprint(sprintId)
  tableData.value = res.data
}

const openAdd = ()=>{
  form.value = { sprintId, status:"TODO", priority:1, estimatePoint:1 }
  isEdit.value = false
  dialogVisible.value = true
}

const openEdit = (row)=>{
  form.value = {...row}
  isEdit.value = true
  dialogVisible.value = true
}

const submit = async ()=>{
  try {
    if(isEdit.value) await updateStory(form.value)
    else await addStory(form.value)
    ElMessage.success("操作成功")
    dialogVisible.value = false
    load()
  }catch(e){}
}

const handleDel = async (id)=>{
  await delStory(id)
  ElMessage.success("删除成功")
  load()
}

onMounted(load)
</script>
