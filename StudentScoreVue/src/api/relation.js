import ajax from'./ajax'
const base="t-relation/"

export default{
    //根据老师编号和成绩编号查询所教课程字段名
    selectCourse:(teaId,scoreId)=>ajax.get(base+"getCourse/"+teaId+"/"+scoreId),
    //分页查询全部信息
    selectPageAll:(pageNow,size)=>ajax.get(base+"selectPageAll/"+pageNow+"/"+size),
    //添加
    addRel:(data)=>ajax.post(base+"addRel",data),
    //根据id删除
    deleteById:(relId)=>ajax.delete(base+"deleteById/"+relId),
    //根据id修改
    updateById:(data)=>ajax.put(base+"updateById",data),
    //根据班级编号或者教师编号或者课程编号查询
    selectById:(data)=>ajax.post(base+"selectByIdList",data),
}