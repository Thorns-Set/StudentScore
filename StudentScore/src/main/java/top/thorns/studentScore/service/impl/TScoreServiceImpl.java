package top.thorns.studentScore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.dto.*;
import top.thorns.studentScore.entity.TScore;
import top.thorns.studentScore.entity.TStudent;
import top.thorns.studentScore.mapper.TScoreMapper;
import top.thorns.studentScore.mapper.TStudentMapper;
import top.thorns.studentScore.service.ITScoreService;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private TStudentMapper tStudentMapper;

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
        if (passRate == null) {
            throw new LoginException(1, "该班级此次考试暂无成绩信息，无法统计");
        }
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
        if (passNum == null) {
            throw new LoginException(1, "该班级此次考试暂无成绩信息，无法统计");
        }
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
        if (tScoreMapper.selectTotalScoreMax(examId, classId).size()==0){
            throw new LoginException(1, "该班级此次考试暂无成绩信息，无法统计");
        }
        return new ScoreMaxDto(tScoreMapper.selectTotalScoreMax(examId, classId),
                tScoreMapper.selectLanguageScoreMax(examId, classId),
                tScoreMapper.selectMathScoreMax(examId, classId),
                tScoreMapper.selectEnglishScoreMax(examId, classId),
                tScoreMapper.selectPoliticsScoreMax(examId, classId),
                tScoreMapper.selectHistoryScoreMax(examId, classId),
                tScoreMapper.selectGeogScoreMax(examId, classId)
        );
    }

    @Override
    public Page<ScoreLIstDto> adminSelectScore(AdminSelectScoreDto dto) {
        Page<ScoreLIstDto> page = new Page<>();
        dto.setPageNow((dto.getPageNow() - 1) * dto.getSize());
        if (dto.getSortName() != null) {
            dto.setSortName(universalMethod(dto.getSortName()));
        }
        log.error(dto.toString());
        List<ScoreLIstDto> list = tScoreMapper.adminSelectScore(dto);
        page.setList(list);
        page.setTotal(tScoreMapper.adminSelectScoreTotal(dto));
        if (list.size() == 0 && dto.getClassId() != null && dto.getExamId() == null) {
            throw new LoginException(1, "该班级暂无成绩信息，请添加");
        }
        if (list.size() == 0 && dto.getClassId() != null && dto.getExamId() != null) {
            throw new LoginException(2, "该班级此次考试暂无成绩信息，请添加");
        }
        if (list.size() == 0 && dto.getClassId() == null && dto.getExamId() != null) {
            throw new LoginException(3, "此次考试暂无成绩信息请添加");
        }
        return page;
    }

    @Override
    public Page<ScoreLIstDto> adminSelectScoreByStuName(AdminSelectScoreDto dto) {
        Page<ScoreLIstDto> page = new Page<>();
        String flag = "%";
        dto.setStuName(flag + dto.getStuName() + flag);
        page.setList(tScoreMapper.adminSelectScoreByStuName(dto));
        if (page.getList().size() == 0) {
            throw new LoginException(1, "没有该学生成绩，请检查学生姓名是否输入正确");
        }
        page.setTotal(page.getList().size());
        log.error(tScoreMapper.adminSelectScoreByStuName(dto).toString());
        return page;
    }

    @Override
    public Integer adminAddScore(TScore tScore) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("stu_id", tScore.getStuId());
        map.put("exam_id", tScore.getExamId());
        QueryWrapper<TScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(map);
        TScore score = tScoreMapper.selectOne(queryWrapper);
        if (tScoreMapper.selectById(tScore.getScoreId()) != null) {
            throw new LoginException(1, "此成绩编号已存在，请修改");
        }
        if (score != null) {
            throw new LoginException(2, "该学生此次考试成绩已经录入");
        }
        TStudent student = tStudentMapper.selectById(tScore.getStuId());
        if (student == null) {
            throw new LoginException(3, "学生编号输入错误");
        }
        if (!Objects.equals(student.getClassId(), tScore.getClassId())) {
            throw new LoginException(4, "学生所对应班级错误，请修改");
        }
        return tScoreMapper.insert(tScore);
    }

    @Override
    public float[] adminSelectPassRate(selectStatistics selectStatistics) {
        PassRate passRate = tScoreMapper.adminSelectPassRate(selectStatistics);
//        将对象类型数据转换为数组
        if (passRate == null) {
            throw new LoginException(1, "该班级此次考试暂无成绩信息，无法统计");
        }
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
    public PassNum adminSelectPassNum(selectStatistics selectStatistics) {
        PassNum passNum = tScoreMapper.adminSelectPassNum(selectStatistics);
        if (passNum == null) {
            throw new LoginException(1, "该班级此次考试暂无成绩信息，无法统计");
        }
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
    public ScoreAvgDto adminSelectScoreAvg(selectStatistics selectStatistics) {
        ScoreAvgDto dto = tScoreMapper.adminSelectScoreAvg(selectStatistics);
        if (dto == null) {
            throw new LoginException(1, "该班级此次考试暂无成绩信息，无法统计");
        }
        return dto;
    }

    @Override
    public ScoreMaxDto adminSelectMax(selectStatistics selectStatistics) {
        if (tScoreMapper.adminSelectTotalScoreMax(selectStatistics).size()==0){
            throw new LoginException(1, "该班级此次考试暂无成绩信息，无法统计");
        }
        return new ScoreMaxDto(tScoreMapper.adminSelectTotalScoreMax(selectStatistics),
                tScoreMapper.adminSelectLanguageScoreMax(selectStatistics),
                tScoreMapper.adminSelectMathScoreMax(selectStatistics),
                tScoreMapper.adminSelectEnglishScoreMax(selectStatistics),
                tScoreMapper.adminSelectPoliticsScoreMax(selectStatistics),
                tScoreMapper.adminSelectHistoryScoreMax(selectStatistics),
                tScoreMapper.adminSelectGeogScoreMax(selectStatistics)
        );
    }

    @Override
    public ScoreAvgDto selectScoreAvg(Integer examId, Integer classId) {
        ScoreAvgDto dto = tScoreMapper.selectScoreAvg(examId, classId);
        if (dto == null) {
            throw new LoginException(1, "该班级此次考试暂无成绩信息，无法统计");
        }
        return dto;
    }
}
