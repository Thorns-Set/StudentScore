package top.thorns.studentScore.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.dto.Page;
import top.thorns.studentScore.dto.selectTeaDto;
import top.thorns.studentScore.entity.TStudent;
import top.thorns.studentScore.entity.TTeacher;
import top.thorns.studentScore.mapper.TTeacherMapper;
import top.thorns.studentScore.service.ITTeacherService;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@Service
public class TTeacherServiceImpl extends ServiceImpl<TTeacherMapper, TTeacher> implements ITTeacherService {
    @Autowired
    private TTeacherMapper tTeacherMapper;
    @Autowired
    private ITTeacherService itTeacherService;

    @Override
    public TTeacher login(Integer user, String password) {
        //根据id获取教师信息
        TTeacher tTeacher = tTeacherMapper.selectById(user);
        //实体类为空说明教职工编号不存在,则抛出异常
        if (tTeacher == null) {
            log.error(String.valueOf(user));
            throw new LoginException(1, "职工编号错误");
        }
        //输入的密码加密后和用户密码比较,如果不一致则抛出异常
        if (!Objects.equals(tTeacher.getTeaPassword(), password)) {
            throw new LoginException(2, "密码错误");
        }
        //未抛出异常则返回tteacher对象
        return tTeacher;
    }

    @Override
    public Integer updatePassword(Integer id, String password, String oldPassword) {
        //根据id获取旧密码
        String flag = tTeacherMapper.selectByIdPassword(id);
        //判断旧密码是否输入正确
        if (!flag.equals(oldPassword)) {
            //不正确则抛出异常
//            System.out.println("旧密码："+oldPassword);
//            System.out.println("flag"+flag);
            throw new LoginException(1, "原密码不正确");
        }
        //正确则修改密码
        return tTeacherMapper.updateByIdPassword(id, password);
    }

    @Override
    public Page<TTeacher> selectByTeaIdOrTeaName(selectTeaDto dto) {
        if (dto.getTeaName()!=null){
            String flag = "%";
            dto.setTeaName(flag + dto.getTeaName() + flag);
        }
        int currentPage = (dto.getPageNow() - 1) * dto.getSize();
        dto.setPageNow(currentPage);
        Page<TTeacher> page =new Page<>();
        page.setList(tTeacherMapper.selectByTeaIdOrTeaNamePage(dto));
        page.setTotal(tTeacherMapper.selectByTeaIdOrTeaNameTotal(dto));
        if (page.getList().size()==0){
            throw new LoginException(1,"没有该教师信息");
        }
        return page;
    }

    @Override
    public Boolean addTeacher(TTeacher teacher) {
        try {
            return itTeacherService.save(teacher);
        }catch (Exception e){
            throw new LoginException(1,"教师编号已存在");
        }
    }

    @Override
    public Integer deleteByTeaId(Integer teaId) {
        try {
            return tTeacherMapper.deleteById(teaId);
        }catch (Exception e){
            throw new LoginException(1,"该教师已绑定班级和课程信息无法删除");
        }
    }


}
