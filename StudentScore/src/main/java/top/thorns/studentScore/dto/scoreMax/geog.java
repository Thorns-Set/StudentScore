package top.thorns.studentScore.dto.scoreMax;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/3/216:06
 * @PackageName:top.thorns.studentScore.dto.scoreMax
 * @ClassName: geog
 * @Description: TODO
 **/
@Data
public class geog {
    /**
     * 地理最高分数学生学号
     */
    private Integer geogScoreID;

    /**
     * 地理最高分数学生姓名
     */
    private String geogScoreName;

    /**
     * 地理最高分数
     */
    private Integer geogScoreMax;

}
