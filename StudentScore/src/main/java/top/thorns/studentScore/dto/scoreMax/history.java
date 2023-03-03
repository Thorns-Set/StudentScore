package top.thorns.studentScore.dto.scoreMax;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/216:05
 * @PackageName:top.thorns.studentScore.dto.scoreMax
 * @ClassName: history
 * @Description: TODO
 **/
@Data
public class history {


    /**
     * 历史最高分数学生学号
     */
    private Integer historyScoreID;

    /**
     * 历史最高分数学生姓名
     */
    private String historyScoreName;


    /**
     * 历史最高分数
     */
    private Integer historyScoreMax;
}
