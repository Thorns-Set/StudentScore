package top.thorns.studentScore.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.R;
import top.thorns.studentScore.dto.TeacherDto;
import top.thorns.studentScore.entity.TTeacher;
import top.thorns.studentScore.service.ITTeacherService;


/**
 * <p>
 *  前端控制器
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

    /**
     * 根据id查询老师个人信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R selectById(@PathVariable Integer id){
        return R.ok().setData(itTeacherService.getById(id));
    }

    /**
     * 根据id修改老师个人消息
     * @param teacher
     * @return
     */
    @PutMapping
    public R update(@RequestBody TTeacher teacher){
        itTeacherService.updateById(teacher);
        return R.ok().setData(teacher);
    }

    /**
     * 修改个人密码
     * @param teacherDto
     * @return
     */
    @PutMapping("updatePassword")
    public R updatePassword(@RequestBody TeacherDto teacherDto){
        try {
//            System.out.println("teacherDto"+teacherDto);
            int flag=itTeacherService.updatePassword(teacherDto.getId(),teacherDto.getPassword(),teacherDto.getOldPassword());
            return R.ok().setData(flag);
        }catch (LoginException ex){
            return R.error().setMessage(ex.getMessage());
        }
    }
}
