package zust.bjx.mall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import zust.bjx.mall.consts.MallConst;
import zust.bjx.mall.enums.ResponseEnum;
import zust.bjx.mall.form.UserLoginForm;
import zust.bjx.mall.form.UserRegisterForm;
import zust.bjx.mall.pojo.User;
import zust.bjx.mall.service.UserService;
import zust.bjx.mall.vo.ResponseVO;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

/**
 * @author EnochStar
 * @title: UserController
 * @projectName mall
 * @description: 用户管理层
 * @date 2020/12/13 20:16
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseVO register(@Valid @RequestBody UserRegisterForm userRegisterForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            log.error("注册提交的参数有误,{} {}",
                    Objects.requireNonNull(bindingResult.getFieldError()).getField()+" "+
                            bindingResult.getFieldError().getDefaultMessage());
            return ResponseVO.error(ResponseEnum.PARAM_ERROR,bindingResult);
        }
        // log.info("username={}",userForm.getUsername());
        User user = new User();
        BeanUtils.copyProperties(userRegisterForm,user);
        return userService.register(user);
    }

    @PostMapping("login")
    public ResponseVO<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                  BindingResult bindingResult,
                                  HttpSession session) {
        if (bindingResult.hasErrors()) {
            log.error("登录参数有误，{} {}",
                    Objects.requireNonNull(bindingResult.getFieldError()).getField()+" "+
                            bindingResult.getFieldError().getDefaultMessage());
            return ResponseVO.error(ResponseEnum.PARAM_ERROR,bindingResult);
        }
        ResponseVO<User> userResponseVO = userService.login(userLoginForm.getUsername(),userLoginForm.getPassword());
        //session需要在登录成功后才能设置
        session.setAttribute(MallConst.CURRENT_USER,userResponseVO.getData());
        return userResponseVO;
    }
    // session保存在内存中，一旦服务器重启就没了 改进：token(本质是Cookie) + redis
    @GetMapping
    public ResponseVO<User> userInfo(HttpSession session) {
        log.info("/user sessionId={}",session.getId());
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return ResponseVO.success(user);
    }
    //TODO 判断登录状态，拦截器
    @PostMapping("/logout")
    /**
     * {@link org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory}
     */
    public ResponseVO<User> logout(HttpSession session){
        log.info("/user/logout sessionId={}",session.getId());
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVO.success();
    }

}
