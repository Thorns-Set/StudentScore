package top.thorns.studentScore.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.entity.TTeacher;
import top.thorns.studentScore.mapper.TTeacherMapper;
import top.thorns.studentScore.service.ITTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@Service
public class TTeacherServiceImpl extends ServiceImpl<TTeacherMapper, TTeacher> implements ITTeacherService {
    @Autowired
    private TTeacherMapper tTeacherMapper;

    @Override
    public TTeacher login(Integer user, String password) {
        //根据id获取教师信息
        TTeacher tTeacher=tTeacherMapper.selectById(user);
        //实体类为空说明教职工编号不存在,则抛出异常
        if(tTeacher==null) {
            log.error(String.valueOf(user));
            throw new LoginException(1, "职工编号错误");
        }
        //输入的密码加密后和用户密码比较,如果不一致则抛出异常
        if(!Objects.equals(tTeacher.getTeaPassword(), password)){
            throw new LoginException(2,"密码错误");
        }
        //未抛出异常则返回tteacher对象
        return tTeacher;
    }

    @Override
    public Integer updatePassword(Integer id, String password, String oldPassword) {
        //根据id获取旧密码
        String flag=tTeacherMapper.selectByIdPassword(id);
        //判断旧密码是否输入正确
        if (!flag.equals(oldPassword)){
            //不正确则抛出异常
//            System.out.println("旧密码："+oldPassword);
//            System.out.println("flag"+flag);
            throw new LoginException(1,"原密码不正确");
        }
        //正确则修改密码
        return tTeacherMapper.updateByIdPassword(id,password);
    }


}
