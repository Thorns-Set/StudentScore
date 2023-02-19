package top.thorns.studentScore.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

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
@TableName("t_relation")
public class TRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级编号
     */
    private Integer classId;

    /**
     * 教师编号
     */
    private Integer teaId;

    /**
     * 课程编号
     */
    private Integer courseId;

}
