package top.thorns.studentScore.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.R;
import top.thorns.studentScore.dto.selectRelDto;
import top.thorns.studentScore.entity.TRelation;
import top.thorns.studentScore.mapper.TRelationMapper;
import top.thorns.studentScore.mapper.TScoreMapper;
import top.thorns.studentScore.service.ITRelationService;

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
@RequestMapping("api/t-relation")
public class TRelationController {
    @Autowired
    private TRelationMapper tRelationMapper;
    @Autowired
    private TScoreMapper tScoreMapper;
    @Autowired
    private ITRelationService itRelationService;

    @GetMapping("getCourse/{teaId}/{scoreId}")
    public R selectCourse(@PathVariable(value = "teaId") Integer teaId, @PathVariable(value = "scoreId") Integer scoreId) {
        Integer classId = tScoreMapper.selectClassIdByScoreId(scoreId);
        return R.ok().setData(tRelationMapper.selectCourseByTeaIdClassId(teaId, classId));
    }

    @GetMapping("selectPageAll/{pageNow}/{size}")
    public R selectPage(@PathVariable("pageNow") Integer pageNow,@PathVariable("size") Integer size){
        return R.ok().setData(itRelationService.selectPage(pageNow,size));
    }

    @PostMapping("addRel")
    public R addRel(@RequestBody TRelation tRelation){
        try {
            return R.ok().setData(itRelationService.addRel(tRelation));
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }

    @DeleteMapping("deleteById/{relId}")
    public R deleteById(@PathVariable("relId") Integer relId){
        return  R.ok().setData(tRelationMapper.deleteById(relId));
    }

    @PutMapping("updateById")
    public R updateById(@RequestBody TRelation tRelation){
        try {
            return R.ok().setData(itRelationService.updateByRelId(tRelation));
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }

    @PostMapping("selectByIdList")
    public R selectByIdList(@RequestBody selectRelDto dto){
        try {
            return R.ok().setData(itRelationService.selectByIdList(dto));
        }catch (LoginException e){
            return R.error().setMessage(e.getMessage());
        }
    }
}
