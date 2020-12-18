package zust.bjx.mall.enums;

import lombok.Getter;

/**
 * @author EnochStar
 * @title: ProductStatusEnum
 * @projectName mall
 * @description: 商品的状态
 * @date 2020/12/18 21:35
 */
@Getter
public enum ProductStatusEnum {
    ON_SALE(1),
    OFF_SALE(2),
    DELETE(3);
    private Integer code;

    ProductStatusEnum(Integer code) {
        this.code = code;
    }
}
