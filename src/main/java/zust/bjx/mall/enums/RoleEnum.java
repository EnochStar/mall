package zust.bjx.mall.enums;

import lombok.Getter;

/**
 * @author EnochStar
 * @title: RoleEnum
 * @projectName mall
 * @description: TODO
 * @date 2020/12/13 19:47
 */
@Getter
public enum  RoleEnum {
    ADMIN(0),
    CUSTOMER(1);
    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }
}
