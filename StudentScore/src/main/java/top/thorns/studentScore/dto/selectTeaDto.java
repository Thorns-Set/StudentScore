package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/1217:42
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: selectTeaDto
 * @Description: TODO
 **/
@Data
public class selectTeaDto {

    private Integer teaId;
    private String teaName;

    private Integer pageNow;
    private Integer size;
}
