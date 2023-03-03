package top.thorns.studentScore.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.thorns.studentScore.dto.PassNum;
import top.thorns.studentScore.dto.PassRate;
import top.thorns.studentScore.dto.ScoreAvgDto;
import top.thorns.studentScore.dto.ScoreLIstDto;
import top.thorns.studentScore.dto.scoreMax.*;
import top.thorns.studentScore.entity.TScore;

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
public interface TScoreMapper extends BaseMapper<TScore> {
    /**
     * 根据考试编号分页查询考试成绩
     *
     * @param examId
     * @param currentPage
     * @param size
     * @return
     */
    List<ScoreLIstDto> scoreExamPage(@Param("examId") Integer examId, @Param("currentPage") Integer currentPage, @Param("pageSize") Integer size, @Param("prop") String prop, @Param("order") String order);

    /**
     * 查询分页总记录数
     *
     * @param examId
     * @return
     */
    Integer selectPageTotal(@Param(("examId")) Integer examId);

    /**
     * 根据学生姓名和考试编号查询具体考试成绩
     *
     * @param stuName
     * @param examId
     * @return
     */
    List<ScoreLIstDto> selectByStuNameScore(@Param("stuName") String stuName, @Param("examId") Integer examId);

    /**
     * 根据考试编号和班级编号查询成绩
     *
     * @param examId
     * @param classId
     * @return
     */
    List<ScoreLIstDto> selectByClassIdExamIdScore(@Param("teaId") Integer teaId, @Param("examId") Integer examId, @Param("classId") Integer classId, @Param("currentPage") Integer currentPage, @Param("pageSize") Integer size, @Param("prop") String prop, @Param("order") String order);

    /**
     * 有考试编号没有班级编号
     * 根据老师编号查询所有所教班级本次考试信息
     *
     * @param examId
     * @param teaId
     * @return
     */
    Integer examIdTotal(@Param("examId") Integer examId, @Param("teaId") Integer teaId);

    /**
     * 有班级编号没有考试编号
     * 查询该班级所有考试信息
     *
     * @param classId
     * @return
     */
    Integer classIdTotal(@Param("classId") Integer classId);

    /**
     * 根据班级编号和考试编号查询
     *
     * @param examId
     * @param classId
     * @return
     */
    Integer examIdClassIdTotal(@Param("examId") Integer examId, @Param("classId") Integer classId);

    /**
     * 根据学生姓名班级编号成绩编号查询信息
     *
     * @param stuName
     * @param examId
     * @param classId
     * @return
     */
    List<ScoreLIstDto> byClassIdExamIdStuNameSelectScore(@Param("teaId") Integer teaId, @Param("stuName") String stuName, @Param("examId") Integer examId, @Param("classId") Integer classId);

    /**
     * 根据成绩编号获取班级编号
     *
     * @param scoreId
     * @return
     */
    Integer selectClassIdByScoreId(Integer scoreId);

    /**
     * 根据成绩编号查询考试编号
     *
     * @param scoreId
     * @return
     */
    Integer selectExamIdByScoreId(Integer scoreId);

    /**
     * 根据班级编号和考试编号获取各科及格人数
     *
     * @param examId
     * @param classId
     * @return
     */
    PassNum selectPassNum(@Param("examId") Integer examId, @Param("classId") Integer classId);

    /**
     * 根据班级编号和考试编号获取各科及格率
     *
     * @param examId
     * @param classId
     * @return
     */
    PassRate selectPassRate(@Param("examId") Integer examId, @Param("classId") Integer classId);

    /**
     * 根据班级编号和考试编号查询出各科平均分数
     *
     * @param examId
     * @param classId
     * @return
     */
    ScoreAvgDto selectScoreAvg(@Param("examId") Integer examId, @Param("classId") Integer classId);

    /**
     * 根据班级编号和考试编号查询出语文最高成绩
     *
     * @param examId
     * @param classId
     * @return
     */
    List<language> selectLanguageScoreMax(@Param("examId") Integer examId, @Param("classId") Integer classId);

    /**
     * 根据班级编号和考试编号查询出数学最高成绩
     *
     * @param examId
     * @param classId
     * @return
     */
    List<math> selectMathScoreMax(@Param("examId") Integer examId, @Param("classId") Integer classId);

    /**
     * 根据班级编号和考试编号查询出总最高成绩
     *
     * @param examId
     * @param classId
     * @return
     */
    List<total> selectTotalScoreMax(@Param("examId") Integer examId, @Param("classId") Integer classId);

    /**
     * 根据班级编号和考试编号查询出英语最高成绩
     *
     * @param examId
     * @param classId
     * @return
     */
    List<english> selectEnglishScoreMax(@Param("examId") Integer examId, @Param("classId") Integer classId);

    /**
     * 根据班级编号和考试编号查询出政治最高成绩
     *
     * @param examId
     * @param classId
     * @return
     */
    List<politics> selectPoliticsScoreMax(@Param("examId") Integer examId, @Param("classId") Integer classId);

    /**
     * 根据班级编号和考试编号查询出历史最高成绩
     *
     * @param examId
     * @param classId
     * @return
     */
    List<history> selectHistoryScoreMax(@Param("examId") Integer examId, @Param("classId") Integer classId);

    /**
     * 根据班级编号和考试编号查询出地理最高成绩
     *
     * @param examId
     * @param classId
     * @return
     */
    List<geog> selectGeogScoreMax(@Param("examId") Integer examId, @Param("classId") Integer classId);

}
