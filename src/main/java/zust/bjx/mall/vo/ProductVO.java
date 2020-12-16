package zust.bjx.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author EnochStar
 * @title: ProductVO
 * @projectName mall
 * @description: TODO
 * @date 2020/12/16 19:45
 */
@Data
public class ProductVO {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;
}
