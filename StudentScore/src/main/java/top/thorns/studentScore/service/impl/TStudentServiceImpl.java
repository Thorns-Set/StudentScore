package top.thorns.studentScore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.dto.Page;
import top.thorns.studentScore.dto.login;
import top.thorns.studentScore.entity.TStudent;
import top.thorns.studentScore.mapper.TStudentMapper;
import top.thorns.studentScore.service.ITStudentService;

import java.util.List;
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
public class TStudentServiceImpl extends ServiceImpl<TStudentMapper, TStudent> implements ITStudentService {

    @Autowired
    private TStudentMapper tStudentMapper;

    @Override
    public Page<TStudent> selectStuInfoByTeaIdPage(Integer teaId, Integer size, Integer pageNow) {
        int currentPage = (pageNow - 1) * size;
        Page<TStudent> page = new Page<>();
        page.setList(tStudentMapper.selectStuInfoByTeaIdPage(teaId, currentPage, size));
        page.setTotal(tStudentMapper.teaIdTotal(teaId));
        if (page.getTotal() == 0) {
            throw new LoginException(1, "该教师暂无所教班级");
        }
        return page;
    }

    @Override
    public List<TStudent> selectByStuNameTeaId(Integer teaId, String stuName) {
        String flag = "%";
        stuName = flag + stuName + flag;
        List<TStudent> list = tStudentMapper.selectByStuNameTeaId(teaId, stuName);
        if (list.size() == 0) {
            throw new LoginException(1, "没有该学生成绩，请检查学生姓名是否输入正确");
        }
        return list;
    }

    @Override
    public Integer updateByStuId(TStudent tStudent) {
        try {
            return tStudentMapper.updateById(tStudent);
        } catch (Exception e) {
            throw new LoginException(1, "班级编号不存在，请重新修改");
        }
    }

    @Override
    public TStudent login(login login) {
        TStudent student = tStudentMapper.selectById(Integer.parseInt(login.getUser()));
        if (student == null) {
            throw new LoginException(1, "学号不存在");
        }
        if (!Objects.equals(login.getPassword(), student.getStuPassword())) {
            throw new LoginException(2, "密码输入错误");
        }
        return student;
    }
}
