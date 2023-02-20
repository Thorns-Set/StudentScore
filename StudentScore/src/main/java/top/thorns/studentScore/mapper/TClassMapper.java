package top.thorns.studentScore.mapper;

import top.thorns.studentScore.entity.TClass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
public interface TClassMapper extends BaseMapper<TClass> {
    /**
     * 根据老师编号查询老师所教班级信息
     * @param teaId
     * @return
     */
    List<TClass> selectByTeaIdList(Integer teaId);
}
