package top.thorns.studentScore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.thorns.studentScore.R;
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

}
