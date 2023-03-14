package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/1118:05
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: selectStuDto
 * @Description: TODO
 **/
@Data
public class selectStuDto {

    private Integer classId;
    private String stuName;

    private Integer pageNow;
    private Integer size;

}
