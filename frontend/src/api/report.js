import request from '../utils/request'

export function getBurndown(sprintId){
  const url = sprintId ? `/report/burndown?sprintId=${sprintId}` : '/report/burndown'
  return request({url, method:'get'})
}
