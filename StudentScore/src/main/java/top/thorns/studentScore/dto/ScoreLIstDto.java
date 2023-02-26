package top.thorns.studentScore.dto;

import lombok.Data;

/**
 * @Author: Thorns
 * @Data:2022/12/921:24
 * @PackageName:top.thorns.studentscore.dto
 * @ClassName: ScoreLIstDto
 * @Description: TODO
 **/
@Data
public class ScoreLIstDto {
    /**
     * 成绩编号
     */
    private Integer scoreId;

    /**
     * 学生编号
     */
    private Integer stuId;

    /**
     * 学生名字
     */
    private String stuName;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 班级名称
     */
    private Integer className;

    /**
     * 语文成绩
     */
    private Integer languageScore;

    /**
     * 数学成绩
     */
    private Integer mathScore;

    /**
     * 英语成绩
     */
    private Integer englishScore;

    /**
     * 政治成绩
     */
    private Integer politicsScore;

    /**
     * 历史成绩
     */
    private Integer historyScore;

    /**
     * 地理成绩
     */
    private Integer geogScore;

    /**
     * 总分
     */
    private Integer totalPoints;

}
