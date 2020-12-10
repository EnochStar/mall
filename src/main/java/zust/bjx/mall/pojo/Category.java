package zust.bjx.mall.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author EnochStar
 * @title: Category
 * @projectName mall
 * @description: TODO
 * @date 2020/12/320:26
 *
 * po(persistent object)
 * pojo(plian ordinary java object)
 */
@Data
public class Category {

    private Integer id;

    private Integer parentId;

    private String name;

    private Integer status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;
}
