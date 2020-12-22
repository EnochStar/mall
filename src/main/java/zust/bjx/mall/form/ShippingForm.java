package zust.bjx.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author EnochStar
 * @title: ShippingForm
 * @projectName mall
 * @description: 前台传入
 * @date 2020/12/21 19:35
 */
@Data
public class ShippingForm {
    @NotBlank
    private String receiverName;
    @NotBlank
    private String receiverPhone;
    @NotBlank
    private String receiverMobile;
    @NotBlank
    private String receiverProvince;
    @NotBlank
    private String receiverCity;
    @NotBlank
    private String receiverDistrict;
    @NotBlank
    private String receiverAddress;
    @NotBlank
    private String receiverZip;
}
