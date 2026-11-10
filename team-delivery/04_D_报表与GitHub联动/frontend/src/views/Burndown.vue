<template>
  <el-card class="burndown-card">
    <template #header>
      <div class="hd">
        <span class="hd-title">迭代燃尽图</span>
        <div class="hd-actions">
          <el-select v-model="selectedSprint" placeholder="选择迭代" style="width:200px" @change="load">
            <el-option v-for="s in sprints" :key="s.id" :label="s.sprintName" :value="s.id"/>
          </el-select>
          <el-button :loading="loading" @click="load">刷新</el-button>
        </div>
      </div>
    </template>

    <el-row :gutter="16" class="stats">
      <el-col :span="6">
        <div class="stat">
          <div class="stat-label">迭代名称</div>
          <div class="stat-value small">{{ meta.sprintName }}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat">
          <div class="stat-label">总故事点</div>
          <div class="stat-value">{{ meta.totalPoint }}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat">
          <div class="stat-label">已完成点数</div>
          <div class="stat-value green">{{ meta.donePoint }}</div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="stat">
          <div class="stat-label">剩余点数</div>
          <div class="stat-value orange">{{ meta.totalPoint - meta.donePoint }}</div>
        </div>
      </el-col>
    </el-row>

    <div v-loading="loading" ref="chartRef" class="chart"></div>
  </el-card>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, nextTick } from 'vue'
import * as echarts from 'echarts'
import { getBurndown } from '../api/report'
import { getSprintByProject } from '../api/sprint'

const chartRef = ref(null)
const sprints = ref([])
const selectedSprint = ref(null)
const loading = ref(false)
const meta = ref({ sprintName: '-', totalPoint: 0, donePoint: 0 })

let chart = null
let ro = null
let lastData = null

// 容器在 Tab 隐藏时宽度为 0，此时不能初始化，否则图表会被挤压到左侧
const ensureChart = () => {
  if (chart) return true
  if (!chartRef.value || chartRef.value.clientWidth === 0) return false
  chart = echarts.init(chartRef.value)
  if (lastData) chart.setOption(buildOption(lastData), true)
  return true
}

const loadSprints = async () => {
  try {
    const res = await getSprintByProject(1)
    sprints.value = res.data || []
    if (sprints.value.length && !selectedSprint.value) {
      selectedSprint.value = sprints.value[0].id
    }
  } catch (e) {}
}

const buildOption = (data) => {
  const total = data.totalPoint || 0
  return {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'cross', label: { backgroundColor: '#6a7985' } },
      valueFormatter: (v) => `${v} 点`
    },
    legend: { data: ['计划剩余', '实际剩余'], right: 12, top: 0, icon: 'roundRect' },
    grid: { left: 12, right: 26, top: 46, bottom: 38, containLabel: true },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: data.xAxis,
      axisLine: { lineStyle: { color: '#dcdfe6' } },
      axisLabel: { color: '#606266' }
    },
    yAxis: {
      type: 'value',
      name: '剩余故事点',
      min: 0,
      max: Math.max(total, 10),
      splitLine: { lineStyle: { color: '#f0f2f5' } },
      axisLabel: { color: '#606266' }
    },
    series: [
      {
        name: '计划剩余',
        type: 'line',
        smooth: true,
        symbol: 'emptyCircle',
        symbolSize: 7,
        data: data.plan,
        lineStyle: { width: 2, type: 'dashed', color: '#909399' },
        itemStyle: { color: '#909399' },
        areaStyle: { color: 'rgba(144,147,153,0.08)' }
      },
      {
        name: '实际剩余',
        type: 'line',
        smooth: true,
        symbol: 'circle',
        symbolSize: 8,
        data: data.real,
        lineStyle: { width: 3, color: '#409eff' },
        itemStyle: { color: '#409eff' },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(64,158,255,0.35)' },
            { offset: 1, color: 'rgba(64,158,255,0.02)' }
          ])
        },
        markPoint: {
          symbol: 'pin',
          symbolSize: 42,
          data: [{ type: 'min', name: '最低剩余' }],
          label: { show: false }
        }
      }
    ]
  }
}

const load = async () => {
  loading.value = true
  try {
    const res = await getBurndown(selectedSprint.value)
    const data = res.data || {}
    meta.value = {
      sprintName: data.sprintName || '-',
      totalPoint: data.totalPoint || 0,
      donePoint: data.donePoint || 0
    }
    lastData = data
    await nextTick()
    if (ensureChart()) {
      chart.setOption(buildOption(data), true)
      chart.resize()
    }
  } catch (e) {
    meta.value = { sprintName: '数据加载失败', totalPoint: 0, donePoint: 0 }
  } finally {
    loading.value = false
  }
}

const onResize = () => chart && chart.resize()

onMounted(async () => {
  await loadSprints()
  await load()
  window.addEventListener('resize', onResize)
  // 监听容器尺寸：Tab 由隐藏变为显示时（宽度 0 -> 实际宽度）自动初始化/重绘
  if (chartRef.value && window.ResizeObserver) {
    ro = new ResizeObserver(() => {
      if (chart) {
        chart.resize()
      } else {
        ensureChart()
      }
    })
    ro.observe(chartRef.value)
  }
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', onResize)
  if (ro) {
    ro.disconnect()
    ro = null
  }
  if (chart) {
    chart.dispose()
    chart = null
  }
})
</script>

<style scoped>
.burndown-card { border-radius: 8px; }
.hd { display: flex; justify-content: space-between; align-items: center; }
.hd-title { font-size: 16px; font-weight: 600; color: #303133; }
.hd-actions { display: flex; gap: 10px; }
.stats { margin: 4px 0 10px; }
.stat { background: #f5f7fa; border-radius: 8px; padding: 14px 16px; }
.stat-label { font-size: 12px; color: #909399; margin-bottom: 6px; }
.stat-value { font-size: 24px; font-weight: 600; color: #303133; }
.stat-value.small { font-size: 16px; line-height: 1.5; }
.stat-value.green { color: #67c23a; }
.stat-value.orange { color: #e6a23c; }
.chart { width: 100%; height: 420px; }
</style>
