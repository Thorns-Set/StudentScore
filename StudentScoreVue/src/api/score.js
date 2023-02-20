import ajax from './ajax'
const base="t-score/";

//ajax.params方法 格式化参数成URLSearchParams

export default{
    //根据考试编号分页查询全部成绩
    selectAllPage: (examId,pageNow,size)=>ajax.get(base+examId+"/"+pageNow+"/"+size),
    //根据考试编号分页查询全部成绩并根据字段排序
    selectAllPageSort:(examId,pageNow,size,sortName,order)=>ajax.get(base+examId+"/"+pageNow+"/"+size+"/"+sortName+"/"+order),
    //根据考试编号和学生姓名模糊查询具体成绩
    selectByStuNameScore:(examId,stuName)=>ajax.get(base+"getScore/"+examId+"/"+stuName),
    //根据考试编号和班级编号查询成绩，或者单独根据考试编号查询，或者单独根据班级编号查询
    selectExamIdClassId:(data)=>ajax.post(base+"classIdExamIdScore",data),
}