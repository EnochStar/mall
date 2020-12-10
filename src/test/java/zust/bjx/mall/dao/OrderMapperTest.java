package zust.bjx.mall.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zust.bjx.mall.pojo.Order;

/**
 * @author EnochStar
 * @title: OrderMapperTest
 * @projectName mall
 * @description: TODO
 * @date 2020/12/619:36
 */
@SpringBootTest
class OrderMapperTest {
    @Autowired
    OrderMapper orderMapper;

    @Test
    void deleteByPrimaryKey() {
    }

    @Test
    void insert() {
    }

    @Test
    void insertSelective() {
    }

    @Test
    void selectByPrimaryKey() {
        Order order = orderMapper.selectByPrimaryKey(1);
        System.out.println(order);
    }

    @Test
    void updateByPrimaryKeySelective() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}