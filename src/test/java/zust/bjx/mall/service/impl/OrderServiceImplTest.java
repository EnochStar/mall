package zust.bjx.mall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zust.bjx.mall.service.OrderService;
import zust.bjx.mall.vo.OrderVO;
import zust.bjx.mall.vo.ResponseVO;

/**
 * @author EnochStar
 * @title: OrderServiceImplTest
 * @projectName mall
 * @description: 订单测试
 * @date 2020/12/22 17:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;
    private Integer uid = 1;
    private Integer shippingId = 4;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void create() {
        ResponseVO<OrderVO> orderVOResponseVO = orderService.create(uid, shippingId);
        log.info("orderVOResponseVO = {}",gson.toJson(orderVOResponseVO));
    }
}