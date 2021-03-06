package zust.bjx.mall.enums;

import lombok.Getter;

/**
 * @author EnochStar
 * @title: OrderStatusEnum
 * @projectName mall
 * @description:
 * @date 2020/12/22 18:43
 */
@Getter
public enum OrderStatusEnum {
    CANCELED(0,"已取消"),
    NO_PAY(10,"未付款"),
    PAYD(20,"已付款"),
    SHIPPED(40,"已发货"),
    TRADE_SUCCESS(50,"交易成功"),
    TRADE_CLOSE(60,"交易关闭"),
            ;
    Integer code;
    String desc;

    OrderStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
