package zust.bjx.mall.service;

import com.github.pagehelper.PageInfo;
import zust.bjx.mall.vo.ProductDetailVO;
import zust.bjx.mall.vo.ResponseVO;

/**
 * @author EnochStar
 * @title: ProductService
 * @projectName mall
 * @description: 商品列表的业务接口
 * @date 2020/12/16 19:52
 */
public interface ProductService {
    ResponseVO<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    ResponseVO<ProductDetailVO> detail(Integer productId);
}
