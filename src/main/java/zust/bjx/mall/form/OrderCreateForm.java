package zust.bjx.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author EnochStar
 * @title: OrderCreateForm
 * @projectName mall
 * @description:
 * @date 2020/12/24 17:14
 */
@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;
}
