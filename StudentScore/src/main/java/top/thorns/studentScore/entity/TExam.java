package top.thorns.studentScore.entity;

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
@TableName("t_exam")
public class TExam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 考试编号
     */
    private Integer examId;

    /**
     * 考试名称
     */
    private String examName;


}
