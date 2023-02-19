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
}
