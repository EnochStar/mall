package zust.bjx.mall.service;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zust.bjx.mall.form.ShippingForm;
import zust.bjx.mall.vo.ResponseVO;

import java.util.Map;

/**
 * @author EnochStar
 * @title: ShippingServiceTest
 * @projectName mall
 * @description: TODO
 * @date 2020/12/21 20:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ShippingServiceTest {
    @Autowired
    private ShippingService shippingService;

    private ShippingForm shippingForm;

    private Integer uid = 1;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Before
    public void before(){
        ShippingForm shippingForm = new ShippingForm();
        shippingForm.setReceiverName("enoch");
        shippingForm.setReceiverAddress("liunan");
        shippingForm.setReceiverCity("wenzhou");
        shippingForm.setReceiverDistrict("lucheng");
        shippingForm.setReceiverMobile("110");
        shippingForm.setReceiverProvince("zhejiang");
        shippingForm.setReceiverZip("???");
        this.shippingForm = shippingForm;
    }

    @Test
    public void add() {
        ResponseVO<Map<String,Integer>> responseVO = shippingService.add(uid,shippingForm);
        log.info("responseVO = {}", gson.toJson(responseVO));
    }

    @Test
    public void delete() {
        ResponseVO responseVO = shippingService.delete(4, 10);
        log.info("responseVO = {}", gson.toJson(responseVO));
    }

    @Test
    public void update() {
        shippingForm.setReceiverCity("杭州");
        ResponseVO responseVO = shippingService.update(uid,12,shippingForm);
        log.info("responseVO = {}", gson.toJson(responseVO));
    }

    @Test
    public void list() {
        ResponseVO<PageInfo> list = shippingService.list(uid, 1, 4);
        log.info("list = {}", gson.toJson(list));
    }
}