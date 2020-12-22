package zust.bjx.mall.service.impl;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zust.bjx.mall.enums.ResponseEnum;
import zust.bjx.mall.service.ProductService;
import zust.bjx.mall.vo.ProductDetailVO;
import zust.bjx.mall.vo.ResponseVO;

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
        ResponseVO<PageInfo> responseVO = productService.list(null,2,1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVO.getStatus());
    }

    @Test
    public void detail(){
        ResponseVO<ProductDetailVO> responseVO = productService.detail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVO.getStatus());
    }
}