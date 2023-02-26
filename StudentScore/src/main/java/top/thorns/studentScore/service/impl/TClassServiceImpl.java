package top.thorns.studentScore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.entity.TClass;
import top.thorns.studentScore.mapper.TClassMapper;
import top.thorns.studentScore.service.ITClassService;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@Service
public class TClassServiceImpl extends ServiceImpl<TClassMapper, TClass> implements ITClassService {

    @Autowired
    private TClassMapper tClassMapper;


    @Override
    public List<TClass> selectByTeaIdList(Integer teaId) {
        List<TClass> list = tClassMapper.selectByTeaIdList(teaId);

        //没有查询到班级信息则抛出异常
        if (list.size() == 0) {
            throw new LoginException(1, "该老师暂无所教班级");
        }

        return list;
    }
}
