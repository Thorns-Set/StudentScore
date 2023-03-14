package top.thorns.studentScore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.R;
import top.thorns.studentScore.dto.Page;
import top.thorns.studentScore.dto.StuUpdatePwd;
import top.thorns.studentScore.dto.selectStuDto;
import top.thorns.studentScore.entity.TStudent;
import top.thorns.studentScore.entity.TTeacher;
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
            return R.ok().setData(itStudentService.updateByStuId(student));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @PostMapping("selectByClassIdOrStuName")
    public R selectByClassIdOrStuName(@RequestBody selectStuDto dto){
        try {
            return R.ok().setData(itStudentService.selectByClassOrStuName(dto));
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }

    @GetMapping("selectPage/{pageNow}/{size}")
    public R selectPage(@PathVariable("size") Integer size,@PathVariable("pageNow") Integer pageNow){
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<TStudent> page=new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNow,size);
        return R.ok().setData(itStudentService.page(page));
    }

    @PostMapping("addStudent")
    public R addStudent(@RequestBody TStudent tStudent){
        try {
            return R.ok().setData(itStudentService.addStudent(tStudent));
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }

    @DeleteMapping("deleteStudent/{stuId}")
    public R deleteStudent(@PathVariable("stuId") Integer stuId){
        try {
            return R.ok().setData(itStudentService.deleteStudent(stuId));
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }

    @GetMapping("selectById/{stuId}")
    public R selectById(@PathVariable("stuId") Integer stuId){
        return R.ok().setData(tStudentMapper.selectById(stuId));
    }

    @PutMapping("updatePassword")
    public R updatePassword(@RequestBody StuUpdatePwd stuUpdatePwd){
        try {
            return R.ok().setData(itStudentService.updatePassword(stuUpdatePwd));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }
}
