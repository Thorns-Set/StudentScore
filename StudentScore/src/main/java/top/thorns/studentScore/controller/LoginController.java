package top.thorns.studentScore.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.R;
import top.thorns.studentScore.entity.TTeacher;
import top.thorns.studentScore.service.ITTeacherService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * @Author: Thorns
 * @Data:2022/11/1721:36
 * @PackageName:top.thorns.studentscore.controller
 * @ClassName: LoginController
 * @Description: TODO
 **/
@Slf4j
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private ITTeacherService itTeacherService;

    /**
     * 登录验证
     *
     * @param user
     * @param password
     * @return
     */
    @PostMapping("teaLogin")
    public R TeaLogin(@NotBlank(message = "教职工编号不能为空")
                              Integer user,
                      @NotEmpty(message = "密码不能为空")
                              String password) {
        try {
            TTeacher tTeacher = itTeacherService.login(user, password);
            return R.ok().setData(tTeacher);
        } catch (LoginException ex) {
            return R.error().setMessage(ex.getMessage());
        }
    }
}
