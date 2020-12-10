package zust.bjx.mall.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import zust.bjx.mall.pojo.Category;

/**
 * @author EnochStar
 * @title: CategoryMapper
 * @projectName mall
 * @description: TODO
 * @date 2020/12/320:44
 */

// @Mapper 可在主类上设置@MapperScan

public interface CategoryMapper {
    @Select("select * from mall_category where id = #{id}")
    Category findById(@Param("id") Integer id);

    Category queryById(Integer id);
}