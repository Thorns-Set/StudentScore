package top.thorns.studentScore.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.R;
import top.thorns.studentScore.entity.TExam;
import top.thorns.studentScore.mapper.TExamMapper;
import top.thorns.studentScore.service.ITExamService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@RestController
@RequestMapping("api/t-exam")
public class TExamController {
    @Autowired
    ITExamService itExamService;

    @Autowired
    TExamMapper tExamMapper;

    /**
     * 获取所有考试信息
     *
     * @return
     */
    @GetMapping("allExam")
    public R selectExamAll() {
        return R.ok().setData(tExamMapper.selectList(null));
    }

    @GetMapping("examPage/{size}/{pageNow}")
    public R selectAllExamPage(@PathVariable Integer size, @PathVariable Integer pageNow){
        Page<TExam> page =new Page<>(pageNow,size);
        return R.ok().setData(itExamService.page(page));
    }

    @PostMapping("addExam")
    public R addExamInfo(@RequestBody TExam tExam){
        return R.ok().setData(itExamService.save(tExam));
    }

    @DeleteMapping("deleteById/{examId}")
    public R deleteById(@PathVariable Integer examId){
        try {
            return R.ok().setData(itExamService.deleteById(examId));
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }

    @PutMapping("updateById")
    public R updateById(@RequestBody TExam tExam){
        return R.ok().setData(tExamMapper.updateById(tExam));
    }

    @GetMapping("selectByID/{examId}")
    public R selectById(@PathVariable Integer examId){
        return R.ok().setData(itExamService.selectById(examId));
    }
}
