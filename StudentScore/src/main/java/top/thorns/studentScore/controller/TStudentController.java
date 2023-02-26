package top.thorns.studentScore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.thorns.studentScore.service.ITStudentService;

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

}
