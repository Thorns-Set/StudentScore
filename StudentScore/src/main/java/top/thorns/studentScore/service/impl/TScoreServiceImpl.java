package top.thorns.studentScore.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.dto.ScoreLIstDto;
import top.thorns.studentScore.entity.TScore;
import top.thorns.studentScore.mapper.TScoreMapper;
import top.thorns.studentScore.service.ITScoreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
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

    @Override
    public List<ScoreLIstDto> selectScoreExamPage(Integer examId,Integer pageNow,Integer size,String sortName,String order) {
        int currentPage=(pageNow-1)*size;
        String prop="";
        if (sortName!=null){
            for (int i = 0; i < sortName.length(); i++) {
                if (Character.isUpperCase(sortName.charAt(i))){
                    String temp=sortName.toLowerCase();
                    String flag=temp.substring(0,i)+"_"+temp.substring(i);
                    if (flag.equals("total_points")){
                        prop=flag;
                    }else {
                        prop="s."+flag;
                    }
                }
            }
        }
        log.info("currentPage:"+currentPage);
        log.info("examId:"+examId);
        log.info("size:"+size);
        log.info("prop:"+prop);
        log.info("order:"+order);
        return tScoreMapper.scoreExamPage(examId,currentPage,size,prop,order);
    }

    @Override
    public List<ScoreLIstDto> selectScoreByStuName(Integer examId, String stuName) {
        String flag="%";
        stuName=flag+stuName+flag;
        List<ScoreLIstDto> scoreLIstDto = tScoreMapper.selectByStuNameScore(stuName,examId);
        if (scoreLIstDto.size()==0){
            throw new LoginException(1,"没有该学生成绩，请检查学生姓名是否输入正确");
        }
        return scoreLIstDto;
    }
}
