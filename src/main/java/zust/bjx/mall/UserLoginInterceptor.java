package zust.bjx.mall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import zust.bjx.mall.consts.MallConst;
import zust.bjx.mall.exception.UserLoginException;
import zust.bjx.mall.pojo.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author EnochStar
 * @title: UserLoginInterceptor
 * @projectName mall
 * @description: 登录拦截器
 * @date 2020/12/14 21:39
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    /**
     * @param request
     * @param response
     * @param handler
     * @return true 表示继续流程 false表示中断
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("preHandle....");
        request.getSession();
        User user = (User) request.getSession().getAttribute(MallConst.CURRENT_USER);
        if (user == null) {
            log.info("user==null");
            throw new UserLoginException();
            // return false;
        }
        return true;
    }
}
