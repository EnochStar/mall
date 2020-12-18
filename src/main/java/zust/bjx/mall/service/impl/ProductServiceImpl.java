package zust.bjx.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.bjx.mall.dao.ProductMapper;
import zust.bjx.mall.enums.ProductStatusEnum;
import zust.bjx.mall.enums.ResponseEnum;
import zust.bjx.mall.pojo.Product;
import zust.bjx.mall.service.CategoryService;
import zust.bjx.mall.service.ProductService;
import zust.bjx.mall.vo.ProductDetailVO;
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
    public ResponseVO<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if (categoryId != null) {
            categoryService.findSubCategoryId(categoryId,categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVO> productVOList = products.stream()
                .map(e -> {
                    ProductVO productVO = new ProductVO();
                    BeanUtils.copyProperties(e, productVO);
                    return productVO;
                }).collect(Collectors.toList());
        PageInfo pageInfo = new PageInfo(products);
        pageInfo.setList(productVOList);
        return ResponseVO.success(pageInfo);
    }

    @Override
    public ResponseVO<ProductDetailVO> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product.getStatus().equals(ProductStatusEnum.DELETE.getCode()) ||
                product.getStatus().equals(ProductStatusEnum.OFF_SALE.getCode()))
            return ResponseVO.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        ProductDetailVO productDetailVO = new ProductDetailVO();
        BeanUtils.copyProperties(product,productDetailVO);
        // 敏感数据的处理
        productDetailVO.setStock(product.getStock() > 100 ? 100 : product.getStock());
        return ResponseVO.success(productDetailVO);
    }
}
