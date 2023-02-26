package top.thorns.studentScore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import top.thorns.studentScore.entity.TClass;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@Repository
public interface TClassMapper extends BaseMapper<TClass> {
    /**
     * 根据老师编号查询老师所教班级信息
     *
     * @param teaId
     * @return
     */
    List<TClass> selectByTeaIdList(Integer teaId);


}
