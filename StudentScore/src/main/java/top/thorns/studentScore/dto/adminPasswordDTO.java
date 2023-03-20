package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/2010:12
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: adminPasswordDTO
 * @Description: TODO
 **/
@Data
public class adminPasswordDTO {
    private String id;
    private String password;
    private String oldPassword;
}
