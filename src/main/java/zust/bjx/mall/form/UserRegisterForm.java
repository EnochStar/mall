package zust.bjx.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author EnochStar
 * @title: UserForm
 * @projectName mall
 * @description: TODO
 * @date 2020/12/13 21:14
 */
@Data
public class UserRegisterForm {
    // @NotEmpty 用于集合
    // @NotNull
    // @NotBlank 用于String 判断空格
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

}
