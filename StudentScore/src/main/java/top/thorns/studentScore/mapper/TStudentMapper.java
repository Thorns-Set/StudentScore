package top.thorns.studentScore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.thorns.studentScore.entity.TStudent;

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
public interface TStudentMapper extends BaseMapper<TStudent> {

    /**
     * 根据教师编号分页查询所教班级所有学生信息
     *
     * @param teaId
     * @param currentPage
     * @param size
     * @return
     */
    List<TStudent> selectStuInfoByTeaIdPage(@Param("teaId") Integer teaId, @Param("currentPage") Integer currentPage, @Param("pageSize") Integer size);

    /**
     * 根据教师编号查询学生信息总数
     *
     * @param teaId
     * @return
     */
    Integer teaIdTotal(@Param("teaId") Integer teaId);

    /**
     * 根据教师编号和学生姓名模糊查询学生信息
     *
     * @param teaId
     * @param stuName
     * @return
     */
    List<TStudent> selectByStuNameTeaId(@Param("teaId") Integer teaId, @Param("stuName") String stuName);
}
