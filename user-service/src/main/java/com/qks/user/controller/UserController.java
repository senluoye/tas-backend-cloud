package com.qks.user.controller;

import com.qks.common.exception.ServiceException;
import com.qks.common.po.User;
import com.qks.common.utils.JwtUtils;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.UserInfo;
import com.qks.user.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:36
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登陆
     * @param user
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/login")
    public ResponseVO<Map<String, Object>> userLogin(@RequestBody User user) throws ServiceException {
        return userService.userLogin(user);
    }

    /**
     * 获取用户信息
     * @return
     * @throws ServiceException
     */
    @RequestMapping("/info")
    public ResponseVO<UserInfo> userInfo(@RequestHeader("token") String token) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        return userService.userInfo(userId);
    }

    @RequestMapping("/authorize")
    @RequestMapping("/revoke-authorize")
    @RequestMapping("/info")
    @RequestMapping("/info/list")
    @RequestMapping("/delete-user")
    @RequestMapping("/add")
    @RequestMapping("/info/password")
    @RequestMapping("/all-department")
    @RequestMapping("/all-job-position-target")
    @RequestMapping("/all-job-doctor-target")
}
