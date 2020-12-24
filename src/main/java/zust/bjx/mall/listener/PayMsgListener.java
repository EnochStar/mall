package zust.bjx.mall.listener;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zust.bjx.mall.pojo.PayInfo;
import zust.bjx.mall.service.OrderService;

/**
 * @author EnochStar
 * @title: PayMsgListener
 * @projectName mall
 * @description:
 * 关于PayInfo正确姿势： pay项目提供client.jar,mall项目引入jar包 ==》 多模块开发
 * @date 2020/12/24 19:39
 */
@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void process(String msg){
        log.info("[接收到消息]=>{}",msg);

        PayInfo payInfo = new Gson().fromJson(msg, PayInfo.class);
        if (payInfo.getPlatformStatus().equals("SUCCESS")) {
            // 修改订单里的状态
            orderService.paid(payInfo.getOrderNo());
        }
    }
}
