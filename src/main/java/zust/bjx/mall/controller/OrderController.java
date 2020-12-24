package zust.bjx.mall.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zust.bjx.mall.consts.MallConst;
import zust.bjx.mall.form.OrderCreateForm;
import zust.bjx.mall.pojo.User;
import zust.bjx.mall.service.OrderService;
import zust.bjx.mall.vo.OrderVO;
import zust.bjx.mall.vo.ResponseVO;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author EnochStar
 * @title: OrderController
 * @projectName mall
 * @description:
 * @date 2020/12/24 17:11
 */
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseVO<OrderVO> create(@Valid @RequestBody OrderCreateForm orderCreateForm,
                                      HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return orderService.create(user.getId(),orderCreateForm.getShippingId());
    }

    @GetMapping("/orders")
    public ResponseVO<PageInfo> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.list(user.getId(),pageNum,pageSize);
    }

    @GetMapping("/orders/{orderNo}")
    public ResponseVO<OrderVO> detail(@PathVariable Long orderNo,
                                      HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(user.getId(),orderNo);
    }

    @PutMapping("/orders/{orderNo}")
    public ResponseVO cancel(@PathVariable Long orderNo,
                             HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(user.getId(), orderNo);
    }
}
