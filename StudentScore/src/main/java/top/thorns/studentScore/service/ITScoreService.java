package top.thorns.studentScore.service;

import top.thorns.studentScore.dto.ScoreLIstDto;
import top.thorns.studentScore.entity.TScore;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
public interface ITScoreService extends IService<TScore> {

    List<ScoreLIstDto> selectScoreExamPage(Integer examId,Integer pageNow,Integer size,String sortName,String order);

    List<ScoreLIstDto> selectScoreByStuName(Integer examId,String stuName);

    List<ScoreLIstDto> selectByClassIdExamIdScore(Integer teaId,Integer examId,Integer classId,Integer pageNow,Integer size,String sortName,String order);

}
