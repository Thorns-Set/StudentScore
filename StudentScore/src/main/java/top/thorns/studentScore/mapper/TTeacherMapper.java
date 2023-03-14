package top.thorns.studentScore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.thorns.studentScore.dto.selectTeaDto;
import top.thorns.studentScore.entity.TTeacher;

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
public interface TTeacherMapper extends BaseMapper<TTeacher> {
    /**
     * 根据id查询密码
     *
     * @param id
     * @return
     */
    String selectByIdPassword(Integer id);


    /**
     * 根据id修改密码
     *
     * @param id
     * @param password
     * @return
     */
    Integer updateByIdPassword(@Param("id") Integer id, @Param("password") String password);

    /**
     * 根据教师编号或者教师姓名分页查询
     * @param dto
     * @return
     */
    List<TTeacher> selectByTeaIdOrTeaNamePage(@Param("dto") selectTeaDto dto);
    Integer selectByTeaIdOrTeaNameTotal(@Param("dto") selectTeaDto dto);

}
