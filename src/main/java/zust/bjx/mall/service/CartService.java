package zust.bjx.mall.service;

import zust.bjx.mall.form.CartAddForm;
import zust.bjx.mall.vo.CartVO;
import zust.bjx.mall.vo.ResponseVO;

/**
 * @author EnochStar
 * @title: CartService
 * @projectName mall
 * @description: 购物车接口
 * @date 2020/12/19 18:38
 */
public interface CartService {
    ResponseVO<CartVO> add(Integer uid, CartAddForm cartAddForm);
    ResponseVO<CartVO> list(Integer uid);
}
