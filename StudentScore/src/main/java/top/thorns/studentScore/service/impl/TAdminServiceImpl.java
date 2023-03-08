package top.thorns.studentScore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.dto.login;
import top.thorns.studentScore.entity.TAdmin;
import top.thorns.studentScore.mapper.TAdminMapper;
import top.thorns.studentScore.service.ITAdminService;

import java.util.Objects;

/**
 * @Author: Thorns
 * @Data:2023/3/620:37
 * @PackageName:top.thorns.studentScore.service.impl
 * @ClassName: TAdminServiceImpl
 * @Description: TODO
 **/
@Service
public class TAdminServiceImpl extends ServiceImpl<TAdminMapper, TAdmin> implements ITAdminService {
    @Autowired
    private TAdminMapper tAdminMapper;


    @Override
    public TAdmin login(login login) {
        TAdmin tAdmin = tAdminMapper.selectById(login.getUser());
        if (tAdmin == null) {
            throw new LoginException(1, "管理员编号不存在！");
        }
        if (!Objects.equals(login.getPassword(), tAdmin.getAdminPassword())) {
            throw new LoginException(2, "密码错误");
        }
        return tAdmin;
    }
}
