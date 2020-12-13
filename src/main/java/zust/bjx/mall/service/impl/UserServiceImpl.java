package zust.bjx.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import zust.bjx.mall.dao.UserMapper;
import zust.bjx.mall.enums.ResponseEnum;
import zust.bjx.mall.enums.RoleEnum;
import zust.bjx.mall.pojo.User;
import zust.bjx.mall.service.UserService;
import zust.bjx.mall.vo.ResponseVO;

import java.nio.charset.StandardCharsets;

/**
 * @author EnochStar
 * @title: UserServiceImpl
 * @projectName mall
 * @description: TODO
 * @date 2020/12/13 19:16
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResponseVO register(User user) {

        // username 不能重复
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if (countByUsername > 0) {
            return ResponseVO.error(ResponseEnum.USERNAME_EXIST);
        }

        // email 不能重复
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if (countByEmail > 0) {
            return ResponseVO.error(ResponseEnum.EMAIL_EXIST);
        }
        user.setRole(RoleEnum.CUSTOMER.getCode());
        //MD5摘要算法(Spring自带)
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        // 写入数据库
        int resultCount = userMapper.insertSelective(user);
        if (resultCount == 0) {
            return ResponseVO.error(ResponseEnum.ERRPR);
        }
        return ResponseVO.success();
    }
}
