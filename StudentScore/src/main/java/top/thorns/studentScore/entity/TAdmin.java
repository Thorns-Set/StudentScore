package top.thorns.studentScore.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Thorns
 * @Data:2023/3/620:31
 * @PackageName:top.thorns.studentScore.entity
 * @ClassName: TAdmin
 * @Description: TODO
 **/
@Data
@TableName("t_admin")
public class TAdmin implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 管理员编号
     */
    @TableId(value = "admin_id", type = IdType.AUTO)
    private String adminId;

    /**
     * 管理员密码
     */
    private String adminPassword;
}
