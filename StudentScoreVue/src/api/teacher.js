import ajax from './ajax'
const base = "t-teacher/";
export default {
    //根据id获取个人信息
    get: (id) => ajax.get(base + id),
    //根据id修改个人信息
    update: (data) => ajax.put(base, data),
    //根据id修改个人密码
    updatePassword: (data) => ajax.put(base + "updatePassword", data),
    //全部教师信息分页查询
    selectPage: (pageNow, size) => ajax.get(base + "selectPage/" + pageNow + "/" + size),
    //根据教师编号或者教师姓名分页查询
    selectByTeaIdOrTeaName:(data)=>ajax.post(base+"selectByTeaIdOrTeaName",data),
    //添加教师信息
    addTeacher:(data)=>ajax.post(base+"addTeacher",data),
    //根据教师编号删除
    deleteById:(teaId)=>ajax.delete(base+"deleteById/"+teaId),
}