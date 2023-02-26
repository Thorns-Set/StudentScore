package top.thorns.studentScore.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.thorns.studentScore.R;
import top.thorns.studentScore.mapper.TRelationMapper;
import top.thorns.studentScore.mapper.TScoreMapper;

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

    @GetMapping("getCourse/{teaId}/{scoreId}")
    public R selectCourse(@PathVariable(value = "teaId") Integer teaId, @PathVariable(value = "scoreId") Integer scoreId) {
        Integer classId = tScoreMapper.selectClassIdByScoreId(scoreId);
        return R.ok().setData(tRelationMapper.selectCourseByTeaIdClassId(teaId, classId));
    }

}
