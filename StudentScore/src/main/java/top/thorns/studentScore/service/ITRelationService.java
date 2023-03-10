package top.thorns.studentScore.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.thorns.studentScore.dto.Page;
import top.thorns.studentScore.dto.RelationDto;
import top.thorns.studentScore.entity.TRelation;
import top.thorns.studentScore.dto.selectRelDto;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Thorns
 * @since 2022-11-16
 */
public interface ITRelationService extends IService<TRelation> {
    Page<RelationDto> selectPage(Integer pageNow,Integer size);

    Boolean addRel(TRelation tRelation);

    Boolean updateByRelId(TRelation tRelation);

    Page<RelationDto> selectByIdList(selectRelDto dto);
}
