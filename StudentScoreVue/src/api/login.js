import ajax from './ajax'
const base = "login/";
export default {
    //登录验证
    login :(p)=>ajax.post(base+"teaLogin",ajax.params(p))
}