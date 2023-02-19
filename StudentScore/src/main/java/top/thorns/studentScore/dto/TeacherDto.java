package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2022/11/2822:23
 * @PackageName:top.thorns.studentscore.dto
 * @ClassName: TeacherDto
 * @Description: 修改密码功能dto包
 **/
@Data
public class TeacherDto {
    //教师编号
    private Integer id;
    //教师新密码
    private String password;
    //教师旧密码
    private String oldPassword;

}
