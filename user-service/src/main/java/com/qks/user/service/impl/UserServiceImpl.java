package com.qks.user.service.impl;

import com.qks.common.exception.ServiceException;
import com.qks.common.po.Job;
import com.qks.common.po.Role;
import com.qks.common.po.User;
import com.qks.common.utils.ComputeUtil;
import com.qks.common.utils.JwtUtils;
import com.qks.common.utils.Response;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.UserInfo;
import com.qks.user.mapper.UserMapper;
import com.qks.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:46
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public ResponseVO<Map<String, Object>> userLogin(User user) throws ServiceException {
        String loginName = user.getLoginName();
        String password = ComputeUtil.decrypt(user.getPassword());
        if ("".equals(loginName) || "".equals(password)) {
            throw new ServiceException("用户名或密码不能为空");
        }
        User userData = userMapper.getUserByStatus(loginName, password);
        if (userData == null || userData.getId() == 0) {
            throw new ServiceException("用户名或密码错误");
        }

        return Response.successMap(JwtUtils.createToken(user.getId()));
    }

    @Override
    public ResponseVO<UserInfo> userInfo(Integer userId) throws ServiceException {
        User user = userMapper.getUserByStatus(userId);
        if (user == null) {
            throw new ServiceException("用户信息不存在");
        }

        Role[] roles = userMapper.getUserRolesByStatus(userId);
        Job job =
        return null;
    }

}
