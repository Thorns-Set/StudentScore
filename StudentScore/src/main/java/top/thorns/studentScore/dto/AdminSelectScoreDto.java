package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/139:58
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: AdminSelectScoreDto
 * @Description: TODO
 **/
@Data
public class AdminSelectScoreDto {
    /**
     * 学生姓名
     */
    private String stuName;
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
