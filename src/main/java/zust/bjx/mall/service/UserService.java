package zust.bjx.mall.service;

import zust.bjx.mall.pojo.User;
import zust.bjx.mall.vo.ResponseVO;

/**
 * @author EnochStar
 * @title: UserService
 * @projectName mall
 * @description: TODO
 * @date 2020/12/13 19:12
 */
public interface UserService {

    /**
     * 注册
     *
     * @param user
     */
    ResponseVO register(User user);
}
