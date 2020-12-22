package zust.bjx.mall.service;

import zust.bjx.mall.form.CartAddForm;
import zust.bjx.mall.form.CartUpdateForm;
import zust.bjx.mall.pojo.Cart;
import zust.bjx.mall.vo.CartVO;
import zust.bjx.mall.vo.ResponseVO;

import java.util.List;

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
    ResponseVO<CartVO> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm);
    ResponseVO<CartVO> delete(Integer uid,Integer productId);
    ResponseVO<CartVO> selectAll(Integer uid);
    ResponseVO<CartVO> unSelectAll(Integer uid);
    ResponseVO<Integer> sum(Integer uid);
    List<Cart> listForCart(Integer uid);
}
