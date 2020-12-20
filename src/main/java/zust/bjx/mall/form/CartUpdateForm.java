package zust.bjx.mall.form;

import lombok.Data;

/**
 * @author EnochStar
 * @title: CartUpdateForm
 * @projectName mall
 * @description: TODO
 * @date 2020/12/20 10:40
 */
@Data
public class CartUpdateForm {
    private Integer quantity;
    private Boolean selected;

    public CartUpdateForm(Integer quantity, Boolean selected) {
        this.quantity = quantity;
        this.selected = selected;
    }
}
