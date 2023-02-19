import ajax from './ajax'
const base = "t-teacher/";
export default {
    //根据id获取个人信息
    get :(id)=>ajax.get(base+id),
    //根据id修改个人信息
    update: (data) =>ajax.put(base,data),
    //根据id修改个人密码
    updatePassword: (data)=>ajax.put(base+"updatePassword",data)
}