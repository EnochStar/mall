package zust.bjx.mall.service;

import zust.bjx.mall.vo.CategoryVO;
import zust.bjx.mall.vo.ResponseVO;

import java.util.List;

/**
 * @author EnochStar
 * @title: CategoryService
 * @projectName mall
 * @description: Category分类接口
 */
public interface CategoryService {

    ResponseVO<List<CategoryVO>> selectAll();
}
