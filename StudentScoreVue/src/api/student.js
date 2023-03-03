import ajax from './ajax'
const base="t-student/"

export default{
    //根据教师编号分页查询所教班级所有学生信息
    selectStuInfoByTeaIdPage:(teaId,pageNow,size)=>ajax.get(base+"getStuPageByTea/"+teaId+"/"+pageNow+"/"+size),
    //根据教师编号和学生姓名模糊查询学生信息
    selectByStuName:(teaId,stuName)=>ajax.get(base+"byStuName/"+teaId+"/"+stuName),
    //根据学号修改
    updateByStuId:(data)=>ajax.put(base+"updateStuInfo",data),
}