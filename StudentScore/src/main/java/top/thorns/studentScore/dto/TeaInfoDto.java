package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/2/2314:25
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: TeaInfoDto
 * @Description: 前端教师个人信息展示dto
 **/
@Data
public class TeaInfoDto {

    /**
     * 教师编号
     */
    private Integer teaId;

    /**
     * 教师姓名
     */
    private String teaName;

    /**
     * 教师电话号码
     */
    private String teaTel;

    /**
     * 教师邮件
     */
    private String teaEmial;

    /**
     * 教师身份证号码
     */
    private String teaIdentity;

    /**
     * 教师所教班级名称
     */
    private String teaClassName;

    /**
     * 教师所教授课程名称
     */
    private String teaCourseName;

}
