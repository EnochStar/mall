package zust.bjx.mall.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zust.bjx.mall.pojo.Category;

/**
 * @author EnochStar
 * @title: CategoryMapperTest
 * @projectName mall
 * @description: TODO
 * @date 2020/12/419:33
 */
@SpringBootTest
class CategoryMapperTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    void contextLoads() {
        Category byId = categoryMapper.findById(100001);
        System.out.println(byId);
    }
    @Test
    void queryByIdTest(){
        Category byId = categoryMapper.queryById(100001);
        System.out.println(byId);
    }
}