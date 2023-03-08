package top.thorns.studentScore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.thorns.studentScore.dto.login;
import top.thorns.studentScore.entity.TAdmin;

/**
 * @Author: Thorns
 * @Data:2023/3/620:36
 * @PackageName:top.thorns.studentScore.service
 * @ClassName: ITAdminService
 * @Description: TODO
 **/
public interface ITAdminService extends IService<TAdmin> {

    TAdmin login(login login);
}
