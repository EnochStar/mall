package zust.bjx.mall.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zust.bjx.mall.enums.ResponseEnum;
import zust.bjx.mall.service.ProductService;
import zust.bjx.mall.vo.ProductVO;
import zust.bjx.mall.vo.ResponseVO;

import java.util.List;

/**
 * @author EnochStar
 * @title: ProductServiceImplTest
 * @projectName mall
 * @description: TODO
 * @date 2020/12/16 20:13
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void list() {
        ResponseVO<List<ProductVO>> responseVO = productService.list(null,1,1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVO.getStatus());
    }
}