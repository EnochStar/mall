package zust.bjx.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author EnochStar
 * @title: CartAddForm
 * @projectName mall
 * @description: 添加商品
 * @date 2020/12/19 18:05
 */
@Data
public class CartAddForm {

    @NotNull
    private Integer productId;

    private Boolean selected = true;
}
