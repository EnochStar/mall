package zust.bjx.mall.service;

import com.github.pagehelper.PageInfo;
import zust.bjx.mall.form.ShippingForm;
import zust.bjx.mall.vo.ResponseVO;

import java.util.Map;

/**
 * @author EnochStar
 * @title: ShippingService
 * @projectName mall
 * @description:
 * @date 2020/12/21 19:39
 */
public interface ShippingService {

    ResponseVO<Map<String,Integer>> add(Integer uid, ShippingForm shippingForm);

    ResponseVO delete(Integer uid, Integer shippingId);

    ResponseVO update(Integer uid, Integer shippingId, ShippingForm shippingForm);

    ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);
}
