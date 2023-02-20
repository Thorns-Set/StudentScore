package top.thorns.studentScore.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.R;
import top.thorns.studentScore.entity.TClass;
import top.thorns.studentScore.service.ITClassService;

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
@RequestMapping("api/t-class")
public class TClassController {

    @Autowired
    private ITClassService itClassService;

    @GetMapping("{teaId}")
    public R byTeaIdList(@PathVariable Integer teaId){
        try {
            List<TClass> list=itClassService.selectByTeaIdList(teaId);
            return R.ok().setData(list);
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }
}
