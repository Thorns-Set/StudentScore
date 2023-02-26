package top.thorns.studentScore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.thorns.studentScore.entity.TTeacher;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
public interface ITTeacherService extends IService<TTeacher> {
    TTeacher login(Integer user, String password);

    Integer updatePassword(Integer id, String password, String oldPassword);
}
