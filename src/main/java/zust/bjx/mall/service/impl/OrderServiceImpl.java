package zust.bjx.mall.service.impl;

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
    // 由底层数据库决定的 而非Java代码决定

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

        // 减库存


        // 更新购物车（选中的商品）


        // orderVO 返回前端


        return null;
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
