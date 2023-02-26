package top.thorns.studentScore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@Getter
@Setter
@TableName("t_score")
public class TScore implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成绩编号
     */
    @TableId(value = "score_id", type = IdType.AUTO)
    private Integer scoreId;

    /**
     * 学生编号
     */
    private Integer stuId;

    /**
     * 考试编号
     */
    private Integer examId;

    /**
     * 班级编号
     */
    private Integer classId;

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
}
