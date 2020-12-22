package zust.bjx.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zust.bjx.mall.consts.MallConst;
import zust.bjx.mall.form.ShippingForm;
import zust.bjx.mall.pojo.User;
import zust.bjx.mall.service.ShippingService;
import zust.bjx.mall.vo.ResponseVO;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author EnochStar
 * @title: ShippingController
 * @projectName mall
 * @description:
 * @date 2020/12/21 21:30
 */
@RestController
public class ShippingController {
    @Autowired
    private ShippingService shippingService;

    @PostMapping("/shippings")
    public ResponseVO add(HttpSession session, @Valid @RequestBody ShippingForm shippingForm) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.add(user.getId(),shippingForm);
    }

    @DeleteMapping("/shippings/{shippingId}")
    public ResponseVO delete(HttpSession session, @PathVariable Integer shippingId) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.delete(user.getId(),shippingId);
    }

    @PutMapping("/shippings/{shippingId}")
    public ResponseVO update(HttpSession session,
                             @PathVariable Integer shippingId,
                             @Valid @RequestBody ShippingForm shippingForm) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.update(user.getId(),shippingId,shippingForm);
    }

    @GetMapping("/shippings")
    public ResponseVO list(HttpSession session,
                           @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false,defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.list(user.getId(), pageNum, pageSize);
    }
}
