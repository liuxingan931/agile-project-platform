import request from '../utils/request'

export function getProjectList(){
  return request({url:'/project/list',method:'get'})
}
export function getProjectById(id){
  return request({url:`/project/${id}`,method:'get'})
}
export function addProject(data){
  return request({url:'/project',method:'post',data})
}
export function updateProject(data){
  return request({url:'/project',method:'put',data})
}
export function delProject(id){
  return request({url:`/project/${id}`,method:'delete'})
}
