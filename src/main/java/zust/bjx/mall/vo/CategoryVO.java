package zust.bjx.mall.vo;

import lombok.Data;

import java.util.List;

/**
 * @author EnochStar
 * @title: CategoryVO
 * @projectName mall
 * @description: 返回Category接口的json
 * @date 2020/12/15 14:49
 */
@Data
public class CategoryVO {
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private List<CategoryVO> subCategories;

}
