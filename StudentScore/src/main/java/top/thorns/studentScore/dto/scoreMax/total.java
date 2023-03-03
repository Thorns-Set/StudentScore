package top.thorns.studentScore.dto.scoreMax;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/216:03
 * @PackageName:top.thorns.studentScore.dto.scoreMax
 * @ClassName: total
 * @Description: TODO
 **/
@Data
public class total {

    /**
     * 总分最高分数学生学号
     */
    private Integer totalScoreID;

    /**
     * 总分最高分数学生姓名
     */
    private String totalScoreName;

    /**
     * 总分最高分数
     */
    private Integer totalScoreMax;
}
