package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/1319:16
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: selectStatistics
 * @Description: 管理员统计模块
 * 根据考试编号或者班级信息统计
 * 两个值其中一个可以为空
 **/
@Data
public class selectStatistics {
    private Integer examId;
    private Integer classId;
}
