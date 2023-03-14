package top.thorns.studentScore.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.R;
import top.thorns.studentScore.entity.TClass;
import top.thorns.studentScore.mapper.TClassMapper;
import top.thorns.studentScore.service.ITClassService;

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
@RequestMapping("api/t-class")
public class TClassController {

    @Autowired
    private ITClassService itClassService;
    @Autowired
    private TClassMapper tClassMapper;

    @GetMapping("{teaId}")
    public R byTeaIdList(@PathVariable Integer teaId) {
        try {
            List<TClass> list = itClassService.selectByTeaIdList(teaId);
            return R.ok().setData(list);
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @GetMapping("classPage/{size}/{pageNow}")
    public R selectAllClassPage(@PathVariable Integer size, @PathVariable Integer pageNow) {
        Page<TClass> page = new Page<>(pageNow, size);
        itClassService.page(page);
        return R.ok().setData(page);
    }

    @PostMapping("addClass")
    public R addClassInfo(@RequestBody TClass tClass) {
        return R.ok().setData(itClassService.save(tClass));
    }

    @DeleteMapping("deleteById/{classId}")
    public R deleteClassById(@PathVariable Integer classId) {
        try {
            return R.ok().setData(itClassService.deleteById(classId));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @PutMapping("updateClass")
    public R updateClassById(@RequestBody TClass tClass) {
        log.error(tClass.toString());
        return R.ok().setData(tClassMapper.updateById(tClass));
    }

    @GetMapping("selectByID/{classId}")
    public R selectById(@PathVariable Integer classId) {
        try {
            return R.ok().setData(itClassService.selectById(classId));
        } catch (LoginException e) {
            return R.error().setMessage(e.getMessage());
        }
    }

    @GetMapping("selectAll")
    public R selectAll(){
        return R.ok().setData(itClassService.list());
    }
}
