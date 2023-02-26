import ajax from './ajax'
const base = "t-score/";

//ajax.params方法 格式化参数成URLSearchParams

export default {
    //根据考试编号分页查询全部成绩
    selectAllPage: (examId, pageNow, size) => ajax.get(base + examId + "/" + pageNow + "/" + size),
    //根据考试编号分页查询全部成绩并根据字段排序
    selectAllPageSort: (examId, pageNow, size, sortName, order) => ajax.get(base + examId + "/" + pageNow + "/" + size + "/" + sortName + "/" + order),
    //根据考试编号和学生姓名模糊查询具体成绩
    selectByStuNameScore: (examId, stuName) => ajax.get(base + "getScore/" + examId + "/" + stuName),
    //根据考试编号和班级编号分页查询全部成绩，或者单独根据考试编号查询，或者单独根据班级编号查询
    selectExamIdClassId: (data) => ajax.post(base + "classIdExamIdScore", data),
    //根据考试编号和班级编号和学生姓名模糊查询学生成绩
    byClassIdExamIdStuNameSelectScore: (data) => ajax.post(base + "byClassIdExamIdStuName", data),
    //根据成绩编号获取班级编号
    selectClassIdByScoreId: (scoreId) => ajax.get(base + "getClassIdByScoreId/" + scoreId),
    //根据id修改成绩
    updateById: (data) => ajax.put(base + "updateById", data),
    //根据成绩编号获取考试编号
    selectExamIdByScoreId: (scoreId) => ajax.get(base + "getExamIdByScoreId/" + scoreId),
    //根基成绩编号和考试编号获取各科及格人数
    selectPassNum: (examId, classId) => ajax.get(base + "getPassNum/" + examId + "/" + classId),
    //根据成绩编号和班级编号获取各科及格率
    selectPassRate: (examId, classId) => ajax.get(base + "getPassRate/" + examId + "/" + classId),
}