package zust.bjx.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import zust.bjx.mall.dao.OrderItemMapper;
import zust.bjx.mall.dao.OrderMapper;
import zust.bjx.mall.dao.ProductMapper;
import zust.bjx.mall.dao.ShippingMapper;
import zust.bjx.mall.enums.OrderStatusEnum;
import zust.bjx.mall.enums.PaymentTypeEnum;
import zust.bjx.mall.enums.ProductStatusEnum;
import zust.bjx.mall.enums.ResponseEnum;
import zust.bjx.mall.pojo.*;
import zust.bjx.mall.service.CartService;
import zust.bjx.mall.service.OrderService;
import zust.bjx.mall.vo.OrderItemVO;
import zust.bjx.mall.vo.OrderVO;
import zust.bjx.mall.vo.ResponseVO;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author EnochStar
 * @title: OrderServiceImpl
 * @projectName mall
 * @description:
 * @date 2020/12/22 16:19
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    // 由底层数据库决定的 而非Java代码决定 默认在出现RuntimeException时回滚

    @Transactional
    public ResponseVO<OrderVO> create(Integer uid, Integer shippingId) {
        // 收货地址校验(总之要查出来的)
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if (shipping == null) {
            return ResponseVO.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        // 获取购物车 校验（是否有商品、库存是否充足）
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(cartList)) {
            return ResponseVO.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }
        // 获取cartList里的products
        Set<Integer> productIds = cartList.stream().map(Cart::getProductId).collect(Collectors.toSet());

        List<Product> productList = productMapper.selectByProductsIdSet(productIds);
        Map<Integer, Product> map = productList.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        List<OrderItem> orderItemList = new ArrayList<>();
        Long orderNo = generataOrderNo();
        for (Cart cart : cartList) {
            // 根据productId查数据库
            Product product = map.get(cart.getProductId());
            // 是否有商品
            if (product == null) {
                return ResponseVO.error(ResponseEnum.PRODUCT_NOT_EXIST);
            }
            // 商品上下架状态
            if (!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())) {
                return ResponseVO.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
            }
            // 库存是否充足
            if (product.getStock() < cart.getQuantity()) {
                return ResponseVO.error(ResponseEnum.PRODUCT_STOCK_ERROR);
            }
            // 生成OrderItem
            OrderItem orderItem = buildOrderItem(uid, orderNo, product, cart.getQuantity());
            orderItemList.add(orderItem);

            // 减库存
            product.setStock(product.getStock() - cart.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if (row <= 0) {
                return ResponseVO.error(ResponseEnum.ERRPR);
            }
        }
        // 计算总价（被选中商品）

        // 生成订单，入库： order 和 order_item, 事务 保证数据同时写入或者同时失败

        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);

        int rowForOrder = orderMapper.insertSelective(order);
        if (rowForOrder <= 0) return ResponseVO.error(ResponseEnum.ERRPR);

        int rowForOrderItem = orderItemMapper.batchinsert(orderItemList);
        if (rowForOrderItem <= 0) {
            return ResponseVO.error(ResponseEnum.ERRPR);
        }

        // 对订单中的每一件商品进行insert操作 用for语句频繁读取内存会造成内存浪费
        // for (OrderItem orderItem : orderItemList) {
        //     orderItemMapper.insertSelective(orderItem);
        // }

        // 更新购物车（选中的商品）
        // Redis有事务（打包命令） 无法回滚 因此不在上面的for语句中执行 确保订单下成功后更新
        for (Cart cart : cartList) {
            cartService.delete(uid, cart.getProductId());
        }

        // orderVO 返回前端
        OrderVO orderVO = buildOrderVO(order, orderItemList, shipping);
        return ResponseVO.success(orderVO);
    }

    @Override
    public ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectByUid(uid);

        Set<Long> orderNoSet = orderList.stream()
                .map(Order::getOrderNo)
                .collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);

        Set<Integer> shippingIdSet = orderList.stream()
                .map(Order::getShippingId)
                .collect(Collectors.toSet());
        List<Shipping> shippingList = shippingMapper.selectByIdSet(shippingIdSet);

        // List转map 根据OrderNo
        Map<Long,List<OrderItem>> orderItemMap = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));

        Map<Integer,Shipping> shippingMap = shippingList.stream().
                collect(Collectors.toMap(Shipping::getId,shipping -> shipping));
        List<OrderVO> orderVOList = new ArrayList<>();
        for (Order order : orderList) {
            OrderVO orderVO = buildOrderVO(order, orderItemMap.get(order.getOrderNo()), shippingMap.get(order.getShippingId()));
            orderVOList.add(orderVO);
        }
        // 传入的是orderList
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVOList);
        // 返回orderItemList

        return ResponseVO.success(pageInfo);
    }

    @Override
    public ResponseVO<OrderVO> detail(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(uid)) {
            return ResponseVO.error(ResponseEnum.ORDER_NOT_EXIT);
        }
        Set<Long> orderNoSet = new HashSet<>();
        orderNoSet.add(order.getOrderNo());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        OrderVO orderVO = buildOrderVO(order, orderItemList, shipping);
        return ResponseVO.success(orderVO);
    }

    @Override
    public ResponseVO cancel(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(uid)){
            return ResponseVO.error(ResponseEnum.ORDER_NOT_EXIT);
        }
        // 只有未付款订单可以取消 根据公司业务
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())) {
            return ResponseVO.error(ResponseEnum.ORDER_STATUS_ERROR);
        }
        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0) {
            return ResponseVO.error(ResponseEnum.ERRPR);
        }
        return ResponseVO.success();
    }

    @Override
    public void paid(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null) {
            throw new RuntimeException(ResponseEnum.ORDER_NOT_EXIT.getDesc() + "订单id:" + orderNo);
        }
        // 只有【未付款】订单可以变成【已付款】
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())) {
            throw new RuntimeException(ResponseEnum.ORDER_STATUS_ERROR.getDesc() + "订单id：" + orderNo);
        }
        order.setStatus(OrderStatusEnum.PAYD.getCode());
        order.setPaymentTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0) {
            throw new RuntimeException("将订单更新为已支付状态失败，订单id:" + orderNo);
        }
    }

    private OrderVO buildOrderVO(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order,orderVO);

        List<OrderItemVO> orderItemVOList = orderItemList.stream().map( e -> {
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(e,orderItemVO);
            return orderItemVO;
        }).collect(Collectors.toList());

        orderVO.setShippingId(shipping.getId());
        orderVO.setOrderItemVOList(orderItemVOList);

        if (shipping != null){
            orderVO.setShippingId(shipping.getId());
            orderVO.setShippingVO(shipping);
        }
        return orderVO;
    }

    private Order buildOrder(Integer uid,
                             Long orderNo,
                             Integer shippingId,
                             List<OrderItem> orderItemList) {
        Order order = new Order();
        BigDecimal payment = orderItemList.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());
        return order;
    }

    /**
     * 企业级： 分布式唯一id/主键
     * @return
     */
    private Long generataOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt(999);
    }

    private OrderItem buildOrderItem(Integer uid, Long orderNO, Product product,
                                     Integer quantity) {
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNO);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return item;
    }
}
