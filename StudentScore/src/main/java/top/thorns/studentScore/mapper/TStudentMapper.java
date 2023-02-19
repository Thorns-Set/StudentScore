package top.thorns.studentScore.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.thorns.studentScore.entity.TStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@Mapper
public interface TStudentMapper extends BaseMapper<TStudent> {

}
