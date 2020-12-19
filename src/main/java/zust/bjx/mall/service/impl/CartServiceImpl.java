package zust.bjx.mall.service.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import zust.bjx.mall.dao.ProductMapper;
import zust.bjx.mall.enums.ProductStatusEnum;
import zust.bjx.mall.enums.ResponseEnum;
import zust.bjx.mall.form.CartAddForm;
import zust.bjx.mall.pojo.Cart;
import zust.bjx.mall.pojo.Product;
import zust.bjx.mall.service.CartService;
import zust.bjx.mall.vo.CartProductVO;
import zust.bjx.mall.vo.CartVO;
import zust.bjx.mall.vo.ResponseVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author EnochStar
 * @title: CartServiceImpl
 * @projectName mall
 * @description: 购物车的实现
 * @date 2020/12/19 18:39
 */
@Service
public class CartServiceImpl implements CartService {

    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    private Gson gson = new Gson();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseVO<CartVO> add(Integer uid, CartAddForm cartAddForm) {
        Integer quantity = 1;
        // 商品是否存在
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        if (product == null) {
            return ResponseVO.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        // 商品是否下架
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVO.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        // 商品库存是否充裕
        if (product.getStock() <= 0) {
            return ResponseVO.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        // 写入到Redis
        // 存给定的数据 对于更新快的数据 一般存放在mysql  保证数据是最新的 如价格 状态 库存等
        HashOperations<String,
                String, String> opsForHash = stringRedisTemplate.opsForHash();
        //每次写入其id值应该自增1
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        String value = opsForHash.get(redisKey,String.valueOf(product.getId()));
        Cart cart;
        // null 或 “” 均返回true
        if (StringUtils.isEmpty(value)) {
            // 没有该商品  新增
            cart = new Cart(product.getId(), quantity, cartAddForm.getSelected());
        }else{
            // 有了    数量加1
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }

                //stringRedisTemplate.opsForValue().set(String.format(CART_REDIS_KEY_TEMPLATE,uid),
        //                 gson.toJson(new Cart(product.getId(), quantity,cartAddForm.getSelected())));
        // 用hash代替list的原因是 list需要遍历 而哈希根据索引求值
        opsForHash.put(String.format(CART_REDIS_KEY_TEMPLATE,uid),
                String.valueOf(product.getId()),
                gson.toJson(cart));
        return list(uid);
    }

    @Override
    public ResponseVO<CartVO> list(Integer uid) {
        HashOperations<String,
                String, String> opsForHash = stringRedisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        CartVO cartVO = new CartVO();
        List<CartProductVO> cartProductVOList = new ArrayList<>();
        boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            //TODO:需要优化，适用mysql里的in
            Product product = productMapper.selectByPrimaryKey(productId);
            if (product != null) {
                CartProductVO cartProductVO = new CartProductVO(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected());
                if (!cart.getProductSelected()) {
                    selectAll = false;
                }
                if (cart.getProductSelected())
                // 注意： 这样才实现了累加  同时只计算选中的
                cartTotalPrice = cartTotalPrice.add(cartProductVO.getProductTotalPrice());

                cartProductVOList.add(cartProductVO);
            }
            cartTotalQuantity += cart.getQuantity();
        }
        cartVO.setCartProductVOList(cartProductVOList);
        cartVO.setSelectedAll(selectAll);
        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartTotalQuantity(cartTotalQuantity);
        return ResponseVO.success(cartVO);
    }
}
