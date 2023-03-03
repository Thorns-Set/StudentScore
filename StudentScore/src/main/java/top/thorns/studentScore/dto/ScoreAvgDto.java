package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/2/2819:30
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: ScoreAvgDto
 * @Description: 前端接收各科平均分数dto
 **/
@Data
public class ScoreAvgDto {

    /**
     * 总平均分
     */
    private float totalScoreAvg;

    /**
     * 语文平均分
     */
    private float languageScoreAvg;

    /**
     * 数学平均分
     */
    private float mathScoreAvg;

    /**
     * 英语平均分
     */
    private float englishScoreAvg;

    /**
     * 政治平均分
     */
    private float politicsScoreAvg;

    /**
     * 历史平均分
     */
    private float historyScoreAvg;

    /**
     * 地理平均分
     */
    private float geogScoreAvg;
}
