package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/2/2019:46
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: ClassExamScoreListDto
 * @Description: 教师班级成绩分页查询前端接收字段
 **/
@Data
public class ClassExamScoreListDto {

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
     * 分页页码
     */
    private Integer pageNow;

    /**
     * 分页大小
     */
    private Integer size;

    /**
     * 排序字段
     */
    private String sortName;

    /**
     * 排序顺序
     */
    private String order;

}
