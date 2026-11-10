import request from '../utils/request'

export function getTaskList(){
  return request({url:'/task/list',method:'get'})
}
export function getTaskBySprint(sid){
  return request({url:`/task/sprint/${sid}`,method:'get'})
}
export function getTaskByStory(storyId){
  return request({url:`/task/story/${storyId}`,method:'get'})
}
export function addTask(data){
  return request({url:'/task',method:'post',data})
}
export function updateTask(data){
  return request({url:'/task',method:'put',data})
}
export function delTask(id){
  return request({url:`/task/${id}`,method:'delete'})
}
