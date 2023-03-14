import ajax from './ajax'
const base="t-student/"

export default{
    //根据教师编号分页查询所教班级所有学生信息
    selectStuInfoByTeaIdPage:(teaId,pageNow,size)=>ajax.get(base+"getStuPageByTea/"+teaId+"/"+pageNow+"/"+size),
    //根据教师编号和学生姓名模糊查询学生信息
    selectByStuName:(teaId,stuName)=>ajax.get(base+"byStuName/"+teaId+"/"+stuName),
    //根据学号修改
    updateByStuId:(data)=>ajax.put(base+"updateStuInfo",data),
    //全部学生分页查询
    selectPage:(pageNow,size)=>ajax.get(base+"selectPage/"+pageNow+"/"+size),
    //根据班级编号或者学生姓名分页查询
    selectByClassIdOrStuName:(data)=>ajax.post(base+"selectByClassIdOrStuName",data),
    //添加学生信息
    addStudent:(data)=>ajax.post(base+"addStudent",data),
    //删除学生信息
    deleteStudent:(stuId)=>ajax.delete(base+"deleteStudent/"+stuId),
    //根据学生姓名查询
    selectById:(stuId)=>ajax.get(base+"selectById/"+stuId),
    //修改密码
    updatePassword:(data)=>ajax.put(base+"updatePassword",data),
}