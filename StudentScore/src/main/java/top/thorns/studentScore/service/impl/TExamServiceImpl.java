package top.thorns.studentScore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.entity.TExam;
import top.thorns.studentScore.mapper.TExamMapper;
import top.thorns.studentScore.service.ITExamService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@Service
public class TExamServiceImpl extends ServiceImpl<TExamMapper, TExam> implements ITExamService {

    @Autowired
    private TExamMapper tExamMapper;

    @Override
    public Integer deleteById(Integer examId) {
        try {
            return tExamMapper.deleteById(examId);
        }catch (Exception e){
            log.error(String.valueOf(e));
            throw new LoginException(1,"此次考试信息已经录入成绩无法删除");
        }
    }

    @Override
    public TExam selectById(Integer examId) {
        TExam tExam = tExamMapper.selectById(examId);
        if (tExam == null) {
            throw new LoginException(1, "该考试编号不存在，请确认没有输入错误");
        }
        return tExam;
    }
}
