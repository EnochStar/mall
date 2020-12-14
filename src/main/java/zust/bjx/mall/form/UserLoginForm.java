package zust.bjx.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author EnochStar
 * @title: UserLoginForm
 * @projectName mall
 * @description: 前端登录对象
 * @date 2020/12/14 15:07
 */
@Data
public class UserLoginForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
