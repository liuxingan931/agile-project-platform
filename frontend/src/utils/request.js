import axios from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 5000
})

service.interceptors.request.use(config => {
  return config
})

service.interceptors.response.use(res => {
  if(res.data.code === 200){
    return res.data
  }else{
    ElMessage.error(res.data.msg)
    return Promise.reject(res.data)
  }
}, err => {
  ElMessage.error("请求失败，请检查后端服务")
  return Promise.reject(err)
})

export default service
