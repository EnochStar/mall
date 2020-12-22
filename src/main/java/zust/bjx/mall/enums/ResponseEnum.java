package zust.bjx.mall.enums;

import lombok.Getter;

/**
 * @author EnochStar
 * @title: ResponseEnum
 * @projectName mall
 * @description: TODO
 * @date 2020/12/13 20:45
 */
@Getter
public enum ResponseEnum {
    ERRPR(-1,"服务端异常"),
    SUCCESS(0,"成功"),
    PASSWORD_ERROR(1,"密码错误"),
    USERNAME_EXIST(2,"用户名已存在"),
    EMAIL_EXIST(4,"邮箱已存在"),
    PARAM_ERROR(3,"参数错误"),
    NEED_LOGIN(10,"用户未登录，请先登录"),
    USERNAME_OR_PASSWORD_ERROR(11,"用户名或者密码错误"),
    PRODUCT_OFF_SALE_OR_DELETE(12,"商品下架或者删除"),
    PRODUCT_NOT_EXIST(13,"商品不存在"),
    PRODUCT_STOCK_ERROR(14,"库存不足"),
    CART_PRODUCT_NOT_EXIST(15,"购物车中该商品不存在"),
    DELETE_SHIPPING_FAIL(16,"删除收货地址失败")
    ;
    Integer code;
    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
