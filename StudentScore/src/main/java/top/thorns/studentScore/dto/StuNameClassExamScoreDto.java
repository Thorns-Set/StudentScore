package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/2/2119:48
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: StuNameClassExamScoreDto
 * @Description: 班级管理模块查询学生个人成绩接收字段
 **/
@Data
public class StuNameClassExamScoreDto {

    /**
     * 教师编号
     */
    private Integer teaId;

    /**
     * 考试编号
     */
    private Integer examId;

    /**
     * 班级编号
     */
    private Integer classId;

    /**
     * 学生姓名
     */
    private String stuName;
}
