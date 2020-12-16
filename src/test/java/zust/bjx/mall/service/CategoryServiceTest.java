package zust.bjx.mall.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zust.bjx.mall.enums.ResponseEnum;
import zust.bjx.mall.vo.CategoryVO;
import zust.bjx.mall.vo.ResponseVO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author EnochStar
 * @title: CategoryServiceTest
 * @projectName mall
 * @description: TODO
 * @date 2020/12/15 16:34
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;
    @Test
    public void selectAll() {
        ResponseVO<List<CategoryVO>> responseVO = categoryService.selectAll();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVO.getStatus());
    }

    @Test
    public void findSubCategoryId(){
        Set<Integer> set = new HashSet<>();
        categoryService.findSubCategoryId(100001,set);
        log.info("set={}",set);
    }
}