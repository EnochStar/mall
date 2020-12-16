package zust.bjx.mall.service;

import zust.bjx.mall.vo.ProductVO;
import zust.bjx.mall.vo.ResponseVO;

import java.util.List;

/**
 * @author EnochStar
 * @title: ProductService
 * @projectName mall
 * @description: 商品列表的业务接口
 * @date 2020/12/16 19:52
 */
public interface ProductService {
    ResponseVO<List<ProductVO>> list(Integer categoryId, Integer pageNum,Integer pageSize);
}
