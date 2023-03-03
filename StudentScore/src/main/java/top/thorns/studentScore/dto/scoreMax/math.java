package top.thorns.studentScore.dto.scoreMax;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/216:02
 * @PackageName:top.thorns.studentScore.dto.scoreMax
 * @ClassName: math
 * @Description: TODO
 **/
@Data
public class math {
    /**
     * 数学最高分数学生学号
     */
    private Integer mathScoreID;

    /**
     * 数学最高分数学生姓名
     */
    private String mathScoreName;


    /**
     * 数学最高分数
     */
    private Integer mathScoreMax;
}
