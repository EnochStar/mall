package zust.bjx.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author EnochStar
 * @title: CarVO
 * @projectName mall
 * @description: 购物车实体类
 * @date 2020/12/19 16:45
 */
@Data
public class CartVO {
    private List<CartProductVO> cartProductVOList;
    private Boolean selectedAll;
    private BigDecimal cartTotalPrice;
    private Integer cartTotalQuantity;
}
