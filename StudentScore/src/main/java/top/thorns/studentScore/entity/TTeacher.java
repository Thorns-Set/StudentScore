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
@TableName("t_teacher")
public class TTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 教师编号
     */
    @TableId(value = "tea_id", type = IdType.AUTO)
    private Integer teaId;

    /**
     * 教师姓名
     */
    private String teaName;

    /**
     * 教师电话号码
     */
    private String teaTel;

    /**
     * 教师邮件
     */
    private String teaEmial;

    /**
     * 教师身份证号码
     */
    private String teaIdentity;

    /**
     * 教师密码
     */
    private String teaPassword;


}
