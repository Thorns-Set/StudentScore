package top.thorns.studentScore.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.thorns.studentScore.entity.TTeacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@Repository
public interface TTeacherMapper extends BaseMapper<TTeacher> {
    /**
     * 根据id查询密码
     * @param id
     * @return
     */
    String selectByIdPassword(Integer id);


    /**
     * 根据id修改密码
     * @param id
     * @param password
     * @return
     */
    int updateByIdPassword(@Param("id") Integer id,@Param("password") String password);
}
