import ajax from './ajax'
const base="t-class/"

export default{
    //根据老师编号查询所教班级信息
    selectByTeaIdList:(teaId)=>ajax.get(base+teaId),
    //分页查询所有班级信息
    selectClassPage:(size,pageNow)=>ajax.get(base+"classPage/"+size+"/"+pageNow),
    //添加班级信息
    addClass:(data)=>ajax.post(base+"addClass",data),
    //根据班级编号删除
    deleteByid:(classId)=>ajax.delete(base+"deleteById/"+classId),
    //根据班级编号修改
    updateByid:(data)=>ajax.put(base+"updateClass",data),
    //根据班级编号查询班级信息
    selectByid:(classId)=>ajax.get(base+"selectByID/"+classId),
    //查询全部班级信息
    selectAll:()=>ajax.get(base+"selectAll"),
}