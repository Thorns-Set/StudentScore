package top.thorns.studentScore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@Data
@TableName("t_class")
public class TClass implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 班级编号
     */
    @TableId(value = "class_Id", type = IdType.AUTO)
    private Integer classId;

    /**
     * 班级名称
     */
    private Integer className;
}
