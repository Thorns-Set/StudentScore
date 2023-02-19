package top.thorns.studentScore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("t_student")
public class TStudent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生学号
     */
    @TableId(value = "stu_id", type = IdType.AUTO)
    private Integer stuId;

    /**
     * 学生姓名
     */
    private String stuName;

    /**
     * 学生电话号码
     */
    private String stuTel;

    /**
     * 学生邮箱
     */
    private String stuEmial;

    /**
     * 学生身份证号码
     */
    private String stuIdentity;

    /**
     * 学生密码
     */
    private String stuPassword;

    /**
     * 学生班级
     */
    private Integer classId;

    /**
     * 学生性别
     */
    private String stuSex;


}
