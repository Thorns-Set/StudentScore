package top.thorns.studentScore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.thorns.studentScore.LoginException;
import top.thorns.studentScore.dto.Page;
import top.thorns.studentScore.dto.RelationDto;
import top.thorns.studentScore.dto.selectRelDto;
import top.thorns.studentScore.entity.TRelation;
import top.thorns.studentScore.mapper.TClassMapper;
import top.thorns.studentScore.mapper.TCourseMapper;
import top.thorns.studentScore.mapper.TRelationMapper;
import top.thorns.studentScore.mapper.TTeacherMapper;
import top.thorns.studentScore.service.ITRelationService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
@Service
public class TRelationServiceImpl extends ServiceImpl<TRelationMapper, TRelation> implements ITRelationService {
    @Autowired
    private ITRelationService itRelationService;

    @Autowired
    private TRelationMapper tRelationMapper;
    @Autowired
    private TTeacherMapper tTeacherMapper;
    @Autowired
    private TCourseMapper tCourseMapper;
    @Autowired
    private TClassMapper tClassMapper;

    @Override
    public Page<RelationDto> selectPage(Integer pageNow, Integer size) {
        log.error(String.valueOf(pageNow));
        log.error(String.valueOf(size));
        Page<RelationDto> page=new Page<>();
        int currentPage = (pageNow - 1) * size;
        page.setList(tRelationMapper.selectPageAll(currentPage,size));
        page.setTotal(tRelationMapper.selectPageTotal());
        return page;
    }

    @Override
    public Boolean addRel(TRelation tRelation) {
        if (tTeacherMapper.selectById(tRelation.getTeaId())==null){
            throw new LoginException(1,"教师编号不存在");
        }
        if (tClassMapper.selectById(tRelation.getClassId())==null){
            throw new LoginException(2,"班级编号不存在");
        }
        if (tCourseMapper.selectById(tRelation.getCourseId())==null){
            throw new LoginException(3,"课程编号不存在");
        }
        if (tRelationMapper.selectByClassIdCourseId(tRelation.getCourseId(),tRelation.getClassId())!=null){
            throw new LoginException(4,"该班级已有教师教学该科目");
        }
        if (tRelationMapper.selectByClassTeaId(tRelation.getTeaId(),tRelation.getClassId())!=null){
            throw new LoginException(5," 教师在该班级已有教学科目");
        }
        try {
            return itRelationService.save(tRelation);
        }catch (LoginException e){
            throw new LoginException(6,"关联id已存在不能重复");
        }

    }

    @Override
    public Boolean updateByRelId(TRelation tRelation) {
        if (tTeacherMapper.selectById(tRelation.getTeaId())==null){
            throw new LoginException(1,"教师编号不存在");
        }
        if (tRelationMapper.selectByClassTeaId(tRelation.getTeaId(),tRelation.getClassId())!=null){
            throw new LoginException(5," 教师在该班级已有教学科目");
        }
        return itRelationService.updateByRelId(tRelation);
    }

    @Override
    public Page<RelationDto> selectByIdList(selectRelDto dto) {
        Page<RelationDto> page = new Page<>();
        page.setList(tRelationMapper.selectByIdList(dto));
        page.setTotal(page.getList().size());
        if(page.getTotal()==0){
            if (dto.getClassId()!=null){
                throw new LoginException(1,"班级编号不存在，请重新输入");
            }else if (dto.getTeaId()!=null){
                throw new LoginException(2,"教师编号不存在，请重新输入");
            }else {
                throw new LoginException(3,"课程编号不存在，请重新输入");
            }
        }
        return page;
    }
}
