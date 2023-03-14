package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/1410:51
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: StuUpdatePwd
 * @Description: TODO
 **/
@Data
public class StuUpdatePwd {
   private Integer stuId;
   private String oldPassword;
   private String password;
}
