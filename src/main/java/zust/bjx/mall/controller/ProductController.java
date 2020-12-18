package zust.bjx.mall.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zust.bjx.mall.service.ProductService;
import zust.bjx.mall.vo.ProductDetailVO;
import zust.bjx.mall.vo.ResponseVO;

/**
 * @author EnochStar
 * @title: ProductController
 * @projectName mall
 * @description: 商品管理控制层
 * @date 2020/12/18 20:55
 */
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    // 问题 default设置 非必传参设置
    public ResponseVO<PageInfo> list(@RequestParam(required = false)Integer categoryId,
                                     @RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return productService.list(categoryId,pageNum,pageSize);
    }

    @GetMapping("/products/{productId}")
    public ResponseVO<ProductDetailVO> detail(@PathVariable Integer productId) {
        return productService.detail(productId);
    }
}
