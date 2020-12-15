package zust.bjx.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import zust.bjx.mall.service.CategoryService;
import zust.bjx.mall.vo.CategoryVO;
import zust.bjx.mall.vo.ResponseVO;

import java.util.List;

/**
 * @author EnochStar
 * @title: CateoryController
 * @projectName mall
 * @description: 商品分类表
 * @date 2020/12/15 15:22
 */
@RestController
public class CateoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/categories")
    public ResponseVO<List<CategoryVO>> selectAll(){
        return categoryService.selectAll();
    }
}
