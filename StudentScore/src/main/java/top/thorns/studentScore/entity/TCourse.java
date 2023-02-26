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
@TableName("t_course")
public class TCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课程编号
     */
    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;

    /**
     * 课程名称
     */
    private String courseName;


}
