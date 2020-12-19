package zust.bjx.mall.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zust.bjx.mall.form.CartAddForm;
import zust.bjx.mall.vo.CartVO;
import zust.bjx.mall.vo.ResponseVO;

import javax.validation.Valid;

/**
 * @author EnochStar
 * @title: CartController
 * @projectName mall
 * @description: 购物车控制层
 * @date 2020/12/19 18:11
 */
@RestController
public class CartController {


    @PostMapping("/carts")
    public ResponseVO<CartVO> add(@Valid @RequestBody CartAddForm cartAddForm) {
        return null;
    }
}
