import ajax from'./ajax'
const base="t-relation/"

export default{
    //根据老师编号和成绩编号查询所教课程字段名
    selectCourse:(teaId,scoreId)=>ajax.get(base+"getCourse/"+teaId+"/"+scoreId),
}