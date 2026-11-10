import request from '../utils/request'

export function getSprintByProject(pid){
  return request({url:`/sprint/project/${pid}`,method:'get'})
}
export function getSprintById(id){
  return request({url:`/sprint/${id}`,method:'get'})
}
export function addSprint(data){
  return request({url:'/sprint',method:'post',data})
}
export function updateSprint(data){
  return request({url:'/sprint',method:'put',data})
}
export function delSprint(id){
  return request({url:`/sprint/${id}`,method:'delete'})
}
