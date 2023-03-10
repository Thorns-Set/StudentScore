package top.thorns.studentScore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.thorns.studentScore.dto.RelationDto;
import top.thorns.studentScore.dto.selectRelDto;
import top.thorns.studentScore.entity.TRelation;

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
public interface TRelationMapper extends BaseMapper<TRelation> {

    /**
     * 根据老师编号和班级编号查询老师所教授课程
     *
     * @param teaId
     * @param classId
     * @return
     */
    String selectCourseByTeaIdClassId(@Param("teaId") Integer teaId, @Param("classId") Integer classId);

    /**
     * 根据老师编号查询其所教全部课程
     * @param teaId
     * @return
     */
    List<String> selectCourseAllByTeaId(Integer teaId);

    /**
     * 分页查询关联信息表
     * @param currentPage
     * @param size
     * @return
     */
    List<RelationDto> selectPageAll(@Param("currentPage") Integer currentPage, @Param("size") Integer size);

    /**
     * 分页查询关联信息表总数
     * @return
     */
    Integer selectPageTotal();

    TRelation selectByClassIdCourseId(@Param("courseId") Integer courseId,@Param("classId") Integer classId);

    TRelation selectByClassTeaId(@Param("teaId") Integer teaId,@Param("classId") Integer classId);

    /**
     * 根据教师编号或者班级编号或者考试编号查询关联表信息
     * @param dto
     * @return
     */
    List<RelationDto> selectByIdList(@Param("dto") selectRelDto dto);

}
