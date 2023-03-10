package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/1015:44
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: selectRelDto
 * @Description: 接收前端查询关联信息表查询条件
 **/
@Data
public class selectRelDto {

   private Integer teaId;

   private Integer classId;

   private Integer courseId;
}
