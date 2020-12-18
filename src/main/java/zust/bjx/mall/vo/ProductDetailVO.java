package zust.bjx.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author EnochStar
 * @title: ProductDetailVO
 * @projectName mall
 * @description: 商品详情实体类
 * @date 2020/12/18 21:23
 */
@Data
public class ProductDetailVO {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImage;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
