package top.thorns.studentScore.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.R;
import top.thorns.studentScore.dto.ClassExamScoreListDto;
import top.thorns.studentScore.dto.Page;
import top.thorns.studentScore.dto.ScoreLIstDto;
import top.thorns.studentScore.entity.TScore;
import top.thorns.studentScore.mapper.TScoreMapper;
import top.thorns.studentScore.service.ITScoreService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@Slf4j
@RestController
@RequestMapping("api/t-score")
public class TScoreController {
    @Autowired
    private ITScoreService itScoreService;

    @Autowired
    private TScoreMapper tScoreMapper;

    /**
     * 根据考试编号分页查询成绩
     * @param examId
     * @param pageNow
     * @param size
     * @return
     */
    //value属性设置两条路径分别对应简单查询成绩和排序查询成绩
    @GetMapping(value = {"{examId}/{pageNow}/{size}/{sortName}/{order}","{examId}/{pageNow}/{size}"})
    public R selectScorePage(@PathVariable(value = "examId") Integer examId, @PathVariable(value = "pageNow") Integer pageNow,@PathVariable(value = "size") Integer size,@PathVariable(value = "sortName" ,required = false) String sortName,@PathVariable(value = "order",required = false) String order){
//        log.info(String.valueOf(pageNow));
//        log.info(String.valueOf(size));
        Page<ScoreLIstDto> page=new Page<>();
        List<ScoreLIstDto> list;
        list=itScoreService.selectScoreExamPage(examId, pageNow, size,sortName,order);
        page.setList(list);
        //获取总记录数
        page.setTotal(tScoreMapper.selectPageTotal(examId));
        return R.ok().setData(page);
    }

    /**
     * 根据考试编号和学生姓名查询具体成绩
     * @param examId
     * @param stuName
     * @return
     */
    @GetMapping("getScore/{examId}/{stuName}")
    public R selectScoreByStuName(@PathVariable(value = "examId") Integer examId,@PathVariable(value = "stuName") String stuName){
        try {
            Page<ScoreLIstDto> page=new Page<>();
            List<ScoreLIstDto> scoreLIstDto=itScoreService.selectScoreByStuName(examId, stuName);
            page.setList(scoreLIstDto);
            page.setTotal(scoreLIstDto.size());
            return R.ok().setData(page);
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }

    @PostMapping("classIdExamIdScore")
    public R selectByClassIdExamIdScore(@RequestBody ClassExamScoreListDto dto){
        Page<ScoreLIstDto> page=new Page<>();
        List<ScoreLIstDto> list;
        log.info(dto.toString());
        list=itScoreService.selectByClassIdExamIdScore(dto.getTeaId(),dto.getExamId(),dto.getClassId(),dto.getPageNow() , dto.getSize(),dto.getSortName(),dto.getOrder());
        page.setList(list);
        //获取总记录数
        if (dto.getExamId()==null){
            page.setTotal(tScoreMapper.classIdTotal(dto.getClassId()));
        }else if(dto.getClassId()==null){
            page.setTotal(tScoreMapper.examIdTotal(dto.getExamId(), dto.getTeaId()));
        }else {
            page.setTotal(tScoreMapper.examIdClassIdTotal(dto.getExamId(),dto.getClassId()));
        }
        return R.ok().setData(page);
    }
}
