package top.thorns.studentScore.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: Thorns
 * @Data:2023/2/1621:37
 * @PackageName:top.thorns.studentScore.dto
 * @ClassName: Page
 * @Description: 分页查询返回集
 **/
@Data
public class Page<T> {
    /**
     * 总条数
     */
    private Integer total;

    /**
     * 分页查询的list集合
     */
    private List<T> list;
}
