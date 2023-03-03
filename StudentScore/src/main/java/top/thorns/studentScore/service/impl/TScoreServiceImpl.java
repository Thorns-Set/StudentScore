package top.thorns.studentScore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.dto.PassNum;
import top.thorns.studentScore.dto.PassRate;
import top.thorns.studentScore.dto.ScoreLIstDto;
import top.thorns.studentScore.dto.ScoreMaxDto;
import top.thorns.studentScore.entity.TScore;
import top.thorns.studentScore.mapper.TScoreMapper;
import top.thorns.studentScore.service.ITScoreService;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@Slf4j
@Service
public class TScoreServiceImpl extends ServiceImpl<TScoreMapper, TScore> implements ITScoreService {

    @Autowired
    private TScoreMapper tScoreMapper;

    public static String universalMethod(String sortName) {
        String prop = "";
        for (int i = 0; i < sortName.length(); i++) {
            if (Character.isUpperCase(sortName.charAt(i))) {
                String temp = sortName.toLowerCase();
                String flag = temp.substring(0, i) + "_" + temp.substring(i);
                if (flag.equals("total_points")) {
                    prop = flag;
                } else {
                    prop = "s." + flag;
                }
            }
        }
        return prop;
    }

    @Override
    public List<ScoreLIstDto> selectScoreExamPage(Integer examId, Integer pageNow, Integer size, String sortName, String order) {
        int currentPage = (pageNow - 1) * size;
        String prop = "";
        if (sortName != null) {
            prop = universalMethod(sortName);
        }
        log.info("currentPage:" + currentPage);
        log.info("examId:" + examId);
        log.info("size:" + size);
        log.info("prop:" + prop);
        log.info("order:" + order);
        return tScoreMapper.scoreExamPage(examId, currentPage, size, prop, order);
    }

    @Override
    public List<ScoreLIstDto> selectScoreByStuName(Integer examId, String stuName) {
        String flag = "%";
        stuName = flag + stuName + flag;
        List<ScoreLIstDto> scoreLIstDto = tScoreMapper.selectByStuNameScore(stuName, examId);
        if (scoreLIstDto.size() == 0) {
            throw new LoginException(1, "没有该学生成绩，请检查学生姓名是否输入正确");
        }
        return scoreLIstDto;
    }

    @Override
    public List<ScoreLIstDto> selectByClassIdExamIdScore(Integer teaId, Integer examId, Integer classId, Integer pageNow, Integer size, String sortName, String order) {
        int currentPage = (pageNow - 1) * size;
        String prop = "";
        if (sortName != null) {
            prop = universalMethod(sortName);
        }
        return tScoreMapper.selectByClassIdExamIdScore(teaId, examId, classId, currentPage, size, prop, order);
    }

    @Override
    public List<ScoreLIstDto> byClassIdExamIdStuNameSelectScore(Integer teaId, Integer examId, Integer ClassId, String stuName) {
        String flag = "%";
        stuName = flag + stuName + flag;
        List<ScoreLIstDto> scoreLIstDto = tScoreMapper.byClassIdExamIdStuNameSelectScore(teaId, stuName, examId, ClassId);
        if (scoreLIstDto.size() == 0) {
            throw new LoginException(1, "没有该学生成绩，请检查学生姓名是否输入正确");
        }
        return scoreLIstDto;
    }

    @Override
    public float[] selectPassRate(Integer examId, Integer classId) {
        PassRate passRate = tScoreMapper.selectPassRate(examId, classId);
//        将对象类型数据转换为数组
        float[] arr = new float[6];
        arr[0] = passRate.getLanguagePassRate();
        arr[1] = passRate.getMathPassRate();
        arr[2] = passRate.getEnglishPassRate();
        arr[3] = passRate.getPoliticsPassRate();
        arr[4] = passRate.getHistoryPassRate();
        arr[5] = passRate.getGeogPassRate();
        return arr;
    }

    @Override
    public PassNum selectPassNum(Integer examId, Integer classId) {
        PassNum passNum = tScoreMapper.selectPassNum(examId, classId);
        //通过总数和及格人数分别计算出各科不及格人数
        passNum.setGeogFlunk(passNum.getTotal() - passNum.getGeogNum());
        passNum.setLanguageFlunk(passNum.getTotal() - passNum.getLanguageNum());
        passNum.setMathFlunk(passNum.getTotal() - passNum.getMathNum());
        passNum.setEnglishFlunk(passNum.getTotal() - passNum.getEnglishNum());
        passNum.setPoliticsFlunk(passNum.getTotal() - passNum.getPoliticsNum());
        passNum.setHistoryFlunk(passNum.getTotal() - passNum.getHistoryNum());
        return passNum;
    }

    @Override
    public ScoreMaxDto selectScoreMax(Integer examId, Integer classId) {
        return new ScoreMaxDto(tScoreMapper.selectTotalScoreMax(examId, classId),
                tScoreMapper.selectLanguageScoreMax(examId, classId),
                tScoreMapper.selectMathScoreMax(examId, classId),
                tScoreMapper.selectEnglishScoreMax(examId, classId),
                tScoreMapper.selectPoliticsScoreMax(examId, classId),
                tScoreMapper.selectHistoryScoreMax(examId, classId),
                tScoreMapper.selectGeogScoreMax(examId, classId)
        );
    }

}
