import ajax from './ajax'
const base="t-exam/"

export default{
    //查询全部考试信息
    selectAllExam:()=>ajax.get(base+"allExam")
}