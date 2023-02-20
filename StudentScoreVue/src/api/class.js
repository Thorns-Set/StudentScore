import ajax from './ajax'
const base="t-class/"

export default{
    //根据老师编号查询所教班级信息
    selectByTeaIdList:(teaId)=>ajax.get(base+teaId),
}