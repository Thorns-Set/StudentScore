package top.thorns.studentScore.dto.scoreMax;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/216:00
 * @PackageName:top.thorns.studentScore.dto.scoreMax
 * @ClassName: language
 * @Description: TODO
 **/
@Data
public class language {

    /**
     * 语文最高分数学生学号
     */
    private Integer languageScoreID;

    /**
     * 语文最高分数学生姓名
     */
    private String languageScoreName;
    /**
     * 语文最高分数
     */
    private Integer languageScoreMax;

}
