import ajax from './ajax'
const base="t-exam/"

export default{
    //查询全部考试信息
    selectAllExam:()=>ajax.get(base+"allExam"),
     //分页查询所有考试信息
     selectExamPage:(size,pageNow)=>ajax.get(base+"examPage/"+size+"/"+pageNow),
     //添加考试信息
     addExam:(data)=>ajax.post(base+"addExam",data),
     //根据考试编号删除
     deleteByid:(examId)=>ajax.delete(base+"deleteById/"+examId),
     //根据考试编号修改
     updateByid:(data)=>ajax.put(base+"updateById",data),
     //根据考试编号查询班级信息
     selectByid:(examId)=>ajax.get(base+"selectByID/"+examId),
    
}