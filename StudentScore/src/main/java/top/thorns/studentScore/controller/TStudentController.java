package top.thorns.studentScore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.R;
import top.thorns.studentScore.dto.Page;
import top.thorns.studentScore.entity.TStudent;
import top.thorns.studentScore.mapper.TStudentMapper;
import top.thorns.studentScore.service.ITStudentService;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@RestController
@RequestMapping("api/t-student")
public class TStudentController {
    @Autowired
    private ITStudentService itStudentService;
    @Autowired
    private TStudentMapper tStudentMapper;

    @GetMapping("getStuPageByTea/{teaId}/{pageNow}/{size}")
    public R studentPageByteaId(@PathVariable("teaId") Integer teaId, @PathVariable("pageNow") Integer pageNow, @PathVariable("size") Integer size) {
        try {
            return R.ok().setData(itStudentService.selectStuInfoByTeaIdPage(teaId, size, pageNow));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @GetMapping("byStuName/{teaId}/{stuName}")
    public R byStuName(@PathVariable("teaId") Integer teaId, @PathVariable("stuName") String stuName) {
        try {
            Page<TStudent> page = new Page<>();
            List<TStudent> list = itStudentService.selectByStuNameTeaId(teaId, stuName);
            page.setList(list);
            page.setTotal(list.size());
            return R.ok().setData(page);
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @PutMapping("updateStuInfo")
    public R update(@RequestBody TStudent student) {
        try {
            return R.ok().setData(tStudentMapper.updateById(student));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }
}
