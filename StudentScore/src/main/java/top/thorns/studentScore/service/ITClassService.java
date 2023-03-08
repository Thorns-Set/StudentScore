package top.thorns.studentScore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.thorns.studentScore.entity.TClass;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
public interface ITClassService extends IService<TClass> {

    List<TClass> selectByTeaIdList(Integer teaId);

    Integer deleteById(Integer classId);

    TClass selectById(Integer classId);
}
