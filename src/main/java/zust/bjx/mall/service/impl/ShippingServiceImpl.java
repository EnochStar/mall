package zust.bjx.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zust.bjx.mall.dao.ShippingMapper;
import zust.bjx.mall.enums.ResponseEnum;
import zust.bjx.mall.form.ShippingForm;
import zust.bjx.mall.pojo.Shipping;
import zust.bjx.mall.service.ShippingService;
import zust.bjx.mall.vo.ResponseVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author EnochStar
 * @title: ShippingServiceImpl
 * @projectName mall
 * @description: TODO
 * @date 2020/12/21 19:46
 */
@Service
public class ShippingServiceImpl implements ShippingService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ResponseVO<Map<String,Integer>> add(Integer uid, ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);
        shipping.setUserId(uid);
        int row = shippingMapper.insertSelective(shipping);
        if (row == 0) {
            return ResponseVO.error(ResponseEnum.ERRPR);
        }
        // 返回字段少 故没必要为此新建一个对象 用Map代替
        Map<String,Integer> map = new HashMap<>();
        map.put("shippingId",shipping.getId());
        // 需要mybatis 中配置 才能返回插入的id值
        return ResponseVO.success(map);
    }

    @Override
    public ResponseVO delete(Integer uid, Integer shippingId) {
        int row = shippingMapper.deleteByIdAndUid(uid, shippingId);
        if (row == 0) {
            return ResponseVO.error(ResponseEnum.DELETE_SHIPPING_FAIL);
        }
        return ResponseVO.success();
    }

    @Override
    public ResponseVO update(Integer uid, Integer shippingId, ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);
        shipping.setUserId(uid);
        shipping.setId(shippingId);
        // 注意时间的设置
        int row = shippingMapper.updateByPrimaryKeySelective(shipping);
        if (row == 0) {
            return ResponseVO.error(ResponseEnum.ERRPR);
        }
        return ResponseVO.success();
    }

    @Override
    public ResponseVO<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> list = shippingMapper.selectByUid(uid);
        PageInfo pageInfo = new PageInfo(list);
        return ResponseVO.success(pageInfo);
    }
}
