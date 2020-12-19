package zust.bjx.mall.pojo;

import lombok.Data;

/**
 * @author EnochStar
 * @title: Cart
 * @projectName mall
 * @description:
 * 属于从数据库中读取出来的数据 然后放在MySQL中
 * 并不是从前端传入的 故不放在form层
 * 也不作为最后的返回 故不放在vo层
 * @date 2020/12/19 19:00
 */
@Data
public class Cart {
    private Integer productId;
    private Integer quantity;
    private Boolean productSelected;

    public Cart(Integer productId, Integer quantity, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productSelected = productSelected;
    }
}
