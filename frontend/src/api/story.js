import request from '../utils/request'

export function getStoryBySprint(sid){
  return request({url:`/story/sprint/${sid}`,method:'get'})
}
export function addStory(data){
  return request({url:'/story',method:'post',data})
}
export function updateStory(data){
  return request({url:'/story',method:'put',data})
}
export function delStory(id){
  return request({url:`/story/${id}`,method:'delete'})
}
