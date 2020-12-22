package zust.bjx.mall.vo;

import lombok.Data;
import zust.bjx.mall.pojo.Shipping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author EnochStar
 * @title: OrderVO
 * @projectName mall
 * @description:
 *
 * @date 2020/12/22 16:08
 */
@Data
public class OrderVO {
    private Long orderNo;

    private Integer userId;

    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Date paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private List<OrderItemVO> orderItemVOList;

    private Integer shippingId;

    private Shipping shippingVO;
}
