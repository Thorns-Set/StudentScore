package top.thorns.studentScore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.thorns.studentScore.entity.TClass;
import top.thorns.studentScore.entity.TExam;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
public interface ITExamService extends IService<TExam> {
    Integer deleteById(Integer examId);

    TExam selectById(Integer examId);
}
