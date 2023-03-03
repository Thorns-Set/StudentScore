package top.thorns.studentScore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.thorns.studentScore.dto.Page;
import top.thorns.studentScore.entity.TStudent;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
public interface ITStudentService extends IService<TStudent> {

    Page<TStudent> selectStuInfoByTeaIdPage(Integer teaId, Integer size, Integer pageNow);

    List<TStudent> selectByStuNameTeaId(Integer teaId, String stuName);

    Integer updateByStuId(TStudent tStudent);
}
