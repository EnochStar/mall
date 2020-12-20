package zust.bjx.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zust.bjx.mall.consts.MallConst;
import zust.bjx.mall.form.CartAddForm;
import zust.bjx.mall.form.CartUpdateForm;
import zust.bjx.mall.pojo.User;
import zust.bjx.mall.service.CartService;
import zust.bjx.mall.vo.CartVO;
import zust.bjx.mall.vo.ResponseVO;

import javax.servlet.http.HttpSession;
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
    @Autowired
    private CartService cartService;

    @GetMapping("/carts")
    public ResponseVO<CartVO> list(HttpSession session) {
        User usr = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.list(usr.getId());
    }

    /**
     * @param cartAddForm
     * @param session
     * @return 统一错误处理 完成入参的错误校验
     */
    @PostMapping("/carts")
    public ResponseVO<CartVO> add(@Valid @RequestBody CartAddForm cartAddForm,
                                  HttpSession session) {
        User usr = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.add(usr.getId(), cartAddForm);
    }

    @PutMapping("/carts/{productId}")
    public ResponseVO<CartVO> update(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateForm cartUpdateForm,
                                     HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.update(user.getId(),productId,cartUpdateForm);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVO<CartVO> delete(HttpSession session,
                                   @PathVariable Integer productId) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.delete(user.getId(),productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVO<CartVO> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVO<CartVO> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVO<Integer> sum(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.sum(user.getId());
    }
}
