package zust.bjx.mall.enums;

import lombok.Getter;

/**
 * @author EnochStar
 * @title: PaymentType
 * @projectName mall
 * @description:
 * @date 2020/12/22 18:40
 */
@Getter
public enum PaymentTypeEnum {
    PAY_ONLINE(1),
            ;
    Integer code;

    PaymentTypeEnum(Integer code) {
        this.code = code;
    }
}
