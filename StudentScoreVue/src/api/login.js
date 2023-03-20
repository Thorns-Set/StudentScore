import ajax from './ajax'
const base = "login/";
export default {
    //教师登录验证
    tealogin :(p)=>ajax.post(base+"teaLogin",ajax.params(p)),
    //管理员登录验证
    adminlogin:(data)=>ajax.post(base+"adminLogin",data),
    //学生登录验证
    stulogin:(data)=>ajax.post(base+"stuLogin",data),
    //修改管理员密码
    adminUpdatePwd:(data)=>ajax.post(base+"adminUpdatePwd",data),
}