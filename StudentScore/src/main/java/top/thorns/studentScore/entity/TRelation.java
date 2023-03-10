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
@TableName("t_relation")
public class TRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关联编号
     */
    @TableId(value = "rel_id", type = IdType.AUTO)
    private Integer relId;

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
