package zust.bjx.mall.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zust.bjx.mall.form.CartAddForm;
import zust.bjx.mall.form.CartUpdateForm;
import zust.bjx.mall.service.CartService;
import zust.bjx.mall.vo.CartVO;
import zust.bjx.mall.vo.ResponseVO;

/**
 * @author EnochStar
 * @title: CartServiceImplTest
 * @projectName mall
 * @description: TODO
 * @date 2020/12/19 19:19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CartServiceImplTest {

    @Autowired
    private CartService cartService;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void add() {
        CartAddForm cartAddForm = new CartAddForm();
        cartAddForm.setProductId(29);
        cartAddForm.setSelected(true);
        cartService.add(1,cartAddForm);
    }
    @Test
    public void list(){
        ResponseVO<CartVO> cartVOResponseVO = cartService.list(1);
        log.info("cartVOResponseVO = {}",gson.toJson(cartVOResponseVO));
    }

    @Test
    public void update() {
        CartUpdateForm cartUpdateForm = new CartUpdateForm(3,false);
        ResponseVO<CartVO> cartVOResponseVO = cartService.update(1,26,cartUpdateForm);
        log.info("cartVOResponseVO = {}",gson.toJson(cartVOResponseVO));
    }
    @Test
    public void delete(){
        ResponseVO<CartVO> cartVOResponseVO = cartService.delete(1, 27);
        log.info("cartVOResponseVO = {}",gson.toJson(cartVOResponseVO));
    }

    @Test
    public void sum(){
        ResponseVO<Integer> responseVO = cartService.sum(1);
        log.info("responseVO = {}",responseVO);
    }
    @Test
    public void selectAll(){
        ResponseVO<CartVO> responseVO = cartService.selectAll(1);
        log.info("responseVO = {}",gson.toJson(responseVO));
    }
    @Test
    public void unSelectAll(){
        ResponseVO<CartVO> responseVO = cartService.unSelectAll(1);
        log.info("responseVO = {}",gson.toJson(responseVO));
    }
}