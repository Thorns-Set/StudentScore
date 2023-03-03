package top.thorns.studentScore.dto.scoreMax;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/216:04
 * @PackageName:top.thorns.studentScore.dto.scoreMax
 * @ClassName: politics
 * @Description: TODO
 **/
@Data
public class politics {
    /**
     * 政治最高分数学生学号
     */
    private Integer politicsScoreID;

    /**
     * 政治最高分数学生姓名
     */
    private String politicsScoreName;

    /**
     * 政治最高分数
     */
    private Integer politicsScoreMax;
}
