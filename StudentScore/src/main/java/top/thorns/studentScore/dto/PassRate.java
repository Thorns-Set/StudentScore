package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2023/2/2622:25
 * @PackageName:top.thorns.studentScore.controller
 * @ClassName: PassRate
 * @Description:各科及格率dto
 **/
@Data
public class PassRate {

    /**
     * 语文及格率
     */
    private float languagePassRate;

    /**
     * 数学及格率
     */
    private float mathPassRate;

    /**
     *英语及格率
     */
    private float englishPassRate;

    /**
     *政治及格率
     */
    private float politicsPassRate;

    /**
     *历史及格率
     */
    private float historyPassRate;

    /**
     *地理及格率
     */
    private float geogPassRate;
}
