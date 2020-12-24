package zust.bjx.mall.service;

import com.github.pagehelper.PageInfo;
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

    ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

    ResponseVO<OrderVO> detail(Integer uid,Long orderNo);

    ResponseVO cancel(Integer uid,Long orderNo);

    void paid(Long orderNo);


}
