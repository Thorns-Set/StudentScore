package top.thorns.studentScore.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.R;
import top.thorns.studentScore.dto.*;
import top.thorns.studentScore.entity.TScore;
import top.thorns.studentScore.mapper.TScoreMapper;
import top.thorns.studentScore.service.ITScoreService;

import java.util.List;

/**
 * <p>
 * 前端控制器
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
     *
     * @param examId
     * @param pageNow
     * @param size
     * @param sortName
     * @param order
     * @return
     */
    //value属性设置两条路径分别对应简单查询成绩和排序查询成绩
    @GetMapping(value = {"{examId}/{pageNow}/{size}/{sortName}/{order}", "{examId}/{pageNow}/{size}"})
    public R selectScorePage(@PathVariable(value = "examId") Integer examId, @PathVariable(value = "pageNow") Integer pageNow, @PathVariable(value = "size") Integer size, @PathVariable(value = "sortName", required = false) String sortName, @PathVariable(value = "order", required = false) String order) {
//        log.info(String.valueOf(pageNow));
//        log.info(String.valueOf(size));
        Page<ScoreLIstDto> page = new Page<>();
        List<ScoreLIstDto> list;
        list = itScoreService.selectScoreExamPage(examId, pageNow, size, sortName, order);
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
    public R selectScoreByStuName(@PathVariable(value = "examId") Integer examId, @PathVariable(value = "stuName") String stuName) {
        try {
            Page<ScoreLIstDto> page = new Page<>();
            List<ScoreLIstDto> scoreLIstDto = itScoreService.selectScoreByStuName(examId, stuName);
            page.setList(scoreLIstDto);
            page.setTotal(scoreLIstDto.size());
            return R.ok().setData(page);
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    /**
     * 教师模块根据考试编号或者班级编号分页查询成绩
     * @param dto
     * @return
     */
    @PostMapping("classIdExamIdScore")
    public R selectByClassIdExamIdScore(@RequestBody ClassExamScoreListDto dto) {
        Page<ScoreLIstDto> page = new Page<>();
        List<ScoreLIstDto> list;
        log.info(dto.toString());
        list = itScoreService.selectByClassIdExamIdScore(dto.getTeaId(), dto.getExamId(), dto.getClassId(), dto.getPageNow(), dto.getSize(), dto.getSortName(), dto.getOrder());
        page.setList(list);
        //获取总记录数
        if (dto.getExamId() == null) {
            page.setTotal(tScoreMapper.classIdTotal(dto.getClassId()));
        } else if (dto.getClassId() == null) {
            page.setTotal(tScoreMapper.examIdTotal(dto.getExamId(), dto.getTeaId()));
        } else {
            page.setTotal(tScoreMapper.examIdClassIdTotal(dto.getExamId(), dto.getClassId()));
        }
        return R.ok().setData(page);
    }

    /**
     * 根据班级编号考试编号学生姓名进行查询
     * @param dto
     * @return
     */
    @PostMapping("byClassIdExamIdStuName")
    public R byClassIdExamIdStuNameSelectScore(@RequestBody StuNameClassExamScoreDto dto) {
        try {
            Page<ScoreLIstDto> page = new Page<>();
            List<ScoreLIstDto> list = itScoreService.byClassIdExamIdStuNameSelectScore(dto.getTeaId(), dto.getExamId(), dto.getClassId(), dto.getStuName());
            page.setList(list);
            page.setTotal(list.size());
            return R.ok().setData(page);
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @GetMapping("getClassIdByScoreId/{scoreId}")
    public R selectClassIdByScoreId(@PathVariable("scoreId") Integer scoreId) {
        return R.ok().setData(tScoreMapper.selectClassIdByScoreId(scoreId));
    }

    @PutMapping("updateById")
    public R upDataByScoreId(@RequestBody TScore dto) {
        return R.ok().setData(itScoreService.updateById(dto));
    }

    @GetMapping("getExamIdByScoreId/{scoreId}")
    public R selectExamIdByScoreId(@PathVariable("scoreId") Integer scoreId) {
        return R.ok().setData(tScoreMapper.selectExamIdByScoreId(scoreId));
    }

    @GetMapping("getPassNum/{examId}/{classId}")
    public R selectPassNum(@PathVariable("examId") Integer examId, @PathVariable("classId") Integer classId) {
        try {
            return R.ok().setData(itScoreService.selectPassNum(examId, classId));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @GetMapping("getPassRate/{examId}/{classId}")
    public R selectPassRate(@PathVariable("examId") Integer examId, @PathVariable("classId") Integer classId) {
        try {
            return R.ok().setData(itScoreService.selectPassRate(examId, classId));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @GetMapping("getScoreAvg/{examId}/{classId}")
    public R selectScoreAvg(@PathVariable("examId") Integer examId, @PathVariable("classId") Integer classId) {

        try {
            return R.ok().setData(itScoreService.selectScoreAvg(examId, classId));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @GetMapping("getScoreMax/{examId}/{classId}")
    public R selectScoreMax(@PathVariable("examId") Integer examId, @PathVariable("classId") Integer classId) {
        try {
            return R.ok().setData(itScoreService.selectScoreMax(examId, classId));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @PostMapping("adminSelectScore")
    public R adminSelectScore(@RequestBody AdminSelectScoreDto dto){
        try {
            return R.ok().setData(itScoreService.adminSelectScore(dto));
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }

    @PostMapping("adminSelectScoreByStuName")
    public R adminSelectScoreByStuName(@RequestBody AdminSelectScoreDto dto){
        try {
            return R.ok().setData(itScoreService.adminSelectScoreByStuName(dto));
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }

    @PostMapping("addScore")
    public R addScore(@RequestBody TScore tScore){
        try {
            return R.ok().setData(itScoreService.adminAddScore(tScore));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @DeleteMapping("deleteScore/{scoreId}")
    public R deleteById(@PathVariable("scoreId") Integer scoreId){
        return R.ok().setData(itScoreService.removeById(scoreId));
    }

    @PostMapping("adminSelectPassNum")
    public R adminSelectPassNum(@RequestBody selectStatistics selectStatistics) {
        try {
            return R.ok().setData(itScoreService.adminSelectPassNum(selectStatistics));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @PostMapping("adminSelectPassRate")
    public R adminSelectPassRate(@RequestBody selectStatistics selectStatistics) {
        try {
            return R.ok().setData(itScoreService.adminSelectPassRate(selectStatistics));
        } catch (LoginException e) {
            float[] arr={0,0,0,0,0,0};
            return R.error().setMessage(e.getMessage()).setData(arr);
        }
    }

    @PostMapping("adminSelectScoreAvg")
    public R adminSelectScoreAvg(@RequestBody selectStatistics selectStatistics) {
        try {
            return R.ok().setData(itScoreService.adminSelectScoreAvg(selectStatistics));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @PostMapping("adminSelectScoreMax")
    public R adminSelectScoreMax(@RequestBody selectStatistics selectStatistics){
        try {
            return R.ok().setData(itScoreService.adminSelectMax(selectStatistics));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }
}
