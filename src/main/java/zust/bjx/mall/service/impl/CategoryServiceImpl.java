package zust.bjx.mall.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.bjx.mall.dao.CategoryMapper;
import zust.bjx.mall.pojo.Category;
import zust.bjx.mall.service.CategoryService;
import zust.bjx.mall.vo.CategoryVO;
import zust.bjx.mall.vo.ResponseVO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static zust.bjx.mall.consts.MallConst.ROOT_PARENT_ID;

/**
 * @author EnochStar
 * @title: CategoryServiceImpl
 * @projectName mall
 * @description:
 * 实现CategoryService接口
 * 难点：多级目录实现
 * @date 2020/12/15 14:54
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @return
     * 根据接口文档 思考应该如何查询？
     * 从根开始查 然后再查子节点 依次向下？   会导致多次读取数据库
     *
     * 由于分类表一般存储的不大 故可以一次性全部查询 减少数据库的访问
     */
    @Override
    public ResponseVO<List<CategoryVO>> selectAll() {
        List<Category> categories = categoryMapper.selectAll();
        // 查出parent_id = 0
        // for (Category category : categories) {
        //     if(category.getParentId().equals(ROOT_PARENT_ID)) {
        //         CategoryVO categoryVo = new CategoryVO();
        //         BeanUtils.copyProperties(category,categoryVo);
        //         categoryVOList.add(categoryVo);
        //     }
        // }

        List<CategoryVO> categoryVOList = categories.stream()
                .filter(e -> e.getParentId().equals(ROOT_PARENT_ID))
                .map(this::convertFromCategory)
                .sorted(Comparator.comparing(CategoryVO::getSortOrder).reversed())
                .collect(Collectors.toList());
        findSubCatetory(categoryVOList,categories);
        return ResponseVO.success(categoryVOList);
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAll();
        findSubCategoryId(id,resultSet,categories);
    }
    private void findSubCategoryId(Integer id, Set<Integer> resultSet,List<Category> categories) {
        for (Category category : categories) {
            if (category.getParentId().equals(id)) {
                resultSet.add(category.getId());
                findSubCategoryId(category.getId(),resultSet,categories);
            }
        }
    }

    private void findSubCatetory(List<CategoryVO> categoryVOList, List<Category> categories) {
        for (CategoryVO categoryVO : categoryVOList) {
            List<CategoryVO> subCategoryVoList = new ArrayList<>();
            for (Category category : categories) {
                // 如果查到内容 设置subCategory 继续往下查
                if (categoryVO.getId().equals(category.getParentId())) {
                    CategoryVO subCategoryVo = convertFromCategory(category);
                    subCategoryVoList.add(subCategoryVo);
                }
            }
            // 对子目录进行排序
            subCategoryVoList.sort(Comparator.comparing(CategoryVO::getSortOrder).reversed());
            // 设置子目录
            categoryVO.setSubCategories(subCategoryVoList);
            // 递归实现多级目录
            findSubCatetory(subCategoryVoList,categories);
        }
    }

    private CategoryVO convertFromCategory(Category category) {
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category,categoryVO);
        return categoryVO;
    }
}
