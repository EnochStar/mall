package zust.bjx.mall.service;

import zust.bjx.mall.vo.OrderVO;
import zust.bjx.mall.vo.ResponseVO;

/**
 * @author EnochStar
 * @title: OrderService
 * @projectName mall
 * @description:
 * @date 2020/12/22 16:17
 */
public interface OrderService {
    ResponseVO<OrderVO> create(Integer uid,Integer shippingId);
}
