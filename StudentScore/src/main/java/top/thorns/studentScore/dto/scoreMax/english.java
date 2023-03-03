package top.thorns.studentScore.dto.scoreMax;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/216:03
 * @PackageName:top.thorns.studentScore.dto.scoreMax
 * @ClassName: english
 * @Description: TODO
 **/
@Data
public class english {
    /**
     * 英语最高分数学生学号
     */
    private Integer englishScoreID;

    /**
     * 英语最高分数学生姓名
     */
    private String englishScoreName;

    /**
     * 英语最高分数
     */
    private Integer englishScoreMax;
}
