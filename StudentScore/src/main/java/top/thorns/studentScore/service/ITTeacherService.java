package top.thorns.studentScore.service;

import top.thorns.studentScore.entity.TTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
public interface ITTeacherService extends IService<TTeacher> {
    TTeacher login(Integer user,String password);

    Integer updatePassword(Integer id,String password,String oldPassword);
}
