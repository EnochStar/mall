package zust.bjx.mall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zust.bjx.mall.enums.ResponseEnum;
import zust.bjx.mall.form.UserForm;
import zust.bjx.mall.pojo.User;
import zust.bjx.mall.service.UserService;
import zust.bjx.mall.vo.ResponseVO;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author EnochStar
 * @title: UserController
 * @projectName mall
 * @description: TODO
 * @date 2020/12/13 20:16
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseVO register(@Valid @RequestBody UserForm userForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            log.error("注册提交的参数有误,{} {}",
                    Objects.requireNonNull(bindingResult.getFieldError()).getField()+" "+
                            bindingResult.getFieldError().getDefaultMessage());
            return ResponseVO.error(ResponseEnum.PARAM_ERROR,bindingResult);
        }
        // log.info("username={}",userForm.getUsername());
        User user = new User();
        BeanUtils.copyProperties(userForm,user);
        return userService.register(user);
    }
}
