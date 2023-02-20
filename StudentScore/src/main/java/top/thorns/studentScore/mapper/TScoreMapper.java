package top.thorns.studentScore.mapper;

import org.apache.ibatis.annotations.Param;
import top.thorns.studentScore.dto.ScoreLIstDto;
import top.thorns.studentScore.entity.TScore;
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
public interface TScoreMapper extends BaseMapper<TScore> {
    /**
     * 根据考试编号分页查询考试成绩
     * @param examId
     * @param currentPage
     * @param size
     * @return
     */
    List<ScoreLIstDto> scoreExamPage(@Param("examId") Integer examId,@Param("currentPage") Integer currentPage,@Param("pageSize") Integer size,@Param("prop") String prop,@Param("order") String order);

    /**
     * 查询分页总记录数
     * @param examId
     * @return
     */
    Integer selectPageTotal(@Param(("examId")) Integer examId);

    /**
     * 根据学生姓名和考试编号查询具体考试成绩
     * @param stuName
     * @param examId
     * @return
     */
    List<ScoreLIstDto> selectByStuNameScore(@Param("stuName") String stuName,@Param("examId") Integer examId);

    /**
     * 根据考试编号和班级编号查询成绩
     * @param examId
     * @param classId
     * @return
     */
    List<ScoreLIstDto> selectByClassIdExamIdScore(@Param("teaId") Integer teaId,@Param("examId") Integer examId,@Param("classId") Integer classId,@Param("currentPage") Integer currentPage,@Param("pageSize") Integer size,@Param("prop") String prop,@Param("order") String order);

    /**
     * 有考试编号没有班级编号
     * 根据老师编号查询所有所教班级本次考试信息
     * @param examId
     * @param teaId
     * @return
     */
    Integer examIdTotal(@Param("examId") Integer examId,@Param("teaId") Integer teaId);

    /**
     * 有班级编号没有考试编号
     * 查询该班级所有考试信息
     * @param classId
     * @return
     */
    Integer classIdTotal(@Param("classId") Integer classId);

    /**
     * 根据班级编号和考试编号查询
     * @param examId
     * @param classId
     * @return
     */
    Integer examIdClassIdTotal(@Param("examId") Integer examId,@Param("classId") Integer classId);
}
