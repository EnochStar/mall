package zust.bjx.mall.dao;

import org.apache.ibatis.annotations.Param;
import zust.bjx.mall.pojo.OrderItem;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    int batchinsert(@Param("orderItemList") List<OrderItem> orderItemList);

}