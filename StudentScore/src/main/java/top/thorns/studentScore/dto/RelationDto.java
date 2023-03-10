package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/921:32
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: RelationDto
 * @Description: TODO
 **/
@Data
public class RelationDto {

    /**
     * 关联编号
     */
    private Integer relId;

    /**
     * 班级编号
     */
    private Integer classId;

    /**
     * 班级名称
     */
    private Integer className;

    /**
     * 教师编号
     */
    private Integer teaId;

    /**
     * 教师名称
     */
    private String teaName;
    /**
     * 课程编号
     */
    private Integer courseId;

    /**
     * 课程名称
     */
    private String courseName;
}
