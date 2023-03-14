package top.thorns.studentScore.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.R;
import top.thorns.studentScore.dto.TeaInfoDto;
import top.thorns.studentScore.dto.TeacherDto;
import top.thorns.studentScore.dto.selectTeaDto;
import top.thorns.studentScore.entity.TClass;
import top.thorns.studentScore.entity.TTeacher;
import top.thorns.studentScore.mapper.TClassMapper;
import top.thorns.studentScore.mapper.TRelationMapper;
import top.thorns.studentScore.service.ITTeacherService;

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
@RequestMapping("api/t-teacher")
public class TTeacherController {

    @Autowired
    private ITTeacherService itTeacherService;

    @Autowired
    private TClassMapper tClassMapper;
    @Autowired
    private TRelationMapper tRelationMapper;

    /**
     * 根据id查询老师个人信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R selectById(@PathVariable Integer id) {
        TeaInfoDto dto=new TeaInfoDto();
        String className="";
        String courseName="";

        TTeacher teacher=itTeacherService.getById(id);
        List<TClass> list=tClassMapper.selectByTeaIdList(id);
        List<String> temp=tRelationMapper.selectCourseAllByTeaId(id);

        for (TClass tClass : list) className += tClass.getClassName()+" ";
        for (String s : temp) courseName += s+" ";

        dto.setTeaCourseName(courseName);
        dto.setTeaEmial(teacher.getTeaEmial());
        dto.setTeaId(teacher.getTeaId());
        dto.setTeaClassName(className);
        dto.setTeaIdentity(teacher.getTeaIdentity());
        dto.setTeaTel(teacher.getTeaTel());
        dto.setTeaName(teacher.getTeaName());

        return R.ok().setData(dto);
    }

    /**
     * 根据id修改老师个人消息
     *
     * @param teacher
     * @return
     */
    @PutMapping
    public R update(@RequestBody TTeacher teacher) {
        itTeacherService.updateById(teacher);
        return R.ok().setData(teacher);
    }

    /**
     * 修改个人密码
     *
     * @param teacherDto
     * @return
     */
    @PutMapping("updatePassword")
    public R updatePassword(@RequestBody TeacherDto teacherDto) {
        try {
//            System.out.println("teacherDto"+teacherDto);
            int flag = itTeacherService.updatePassword(teacherDto.getId(), teacherDto.getPassword(), teacherDto.getOldPassword());
            return R.ok().setData(flag);
        } catch (LoginException ex) {
            return R.error().setMessage(ex.getMessage());
        }
    }

    @GetMapping("selectPage/{pageNow}/{size}")
    public R selectPage(@PathVariable("size") Integer size,@PathVariable("pageNow") Integer pageNow){
        Page<TTeacher> page=new Page<>(pageNow,size);
        return R.ok().setData(itTeacherService.page(page));
    }

    @PostMapping("selectByTeaIdOrTeaName")
    public R selectByTeaIdOrTeaName(@RequestBody selectTeaDto dto){
        try {
            return R.ok().setData(itTeacherService.selectByTeaIdOrTeaName(dto));
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }

    @PostMapping("addTeacher")
    public R addTeacher (@RequestBody TTeacher teacher){
        try {
            return R.ok().setData(itTeacherService.addTeacher(teacher));
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }

    @DeleteMapping("deleteById/{teaId}")
    public R deleteByTeaId(@PathVariable("teaId") Integer teaId){
        try {
            return R.ok().setData(itTeacherService.deleteByTeaId(teaId));
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }
}
