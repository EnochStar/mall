package zust.bjx.mall.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.bjx.mall.dao.ProductMapper;
import zust.bjx.mall.service.CategoryService;
import zust.bjx.mall.service.ProductService;
import zust.bjx.mall.vo.ProductVO;
import zust.bjx.mall.vo.ResponseVO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author EnochStar
 * @title: ProductServiceImpl
 * @projectName mall
 * @description: 商品具体业务实现
 * @date 2020/12/16 19:54
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVO<List<ProductVO>> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if (categoryId != null) {
            categoryService.findSubCategoryId(categoryId,categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        List<ProductVO> productVOList = productMapper.selectByCategoryIdSet(categoryIdSet).stream()
                .map(e -> {
                    ProductVO productVO = new ProductVO();
                    BeanUtils.copyProperties(e, productVO);
                    return productVO;
                }).collect(Collectors.toList());
        return ResponseVO.success(productVOList);
    }
}
