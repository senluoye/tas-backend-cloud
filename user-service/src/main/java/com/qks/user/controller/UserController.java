package com.qks.user.controller;

import com.qks.common.dto.user.UserDTO;
import com.qks.common.exception.ServiceException;
import com.qks.common.po.User;
import com.qks.common.po.UserRoleRelations;
import com.qks.common.utils.JwtUtils;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.UserInfo;
import com.qks.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @PostMapping("/login")
    public ResponseVO<Map<String, Object>> userLogin(@RequestBody UserDTO user) throws ServiceException {
        return userService.userLogin(user);
    }

    @PostMapping("/register")
    public ResponseVO<Integer> userRegister(@RequestBody UserDTO user) throws ServiceException {
        return userService.userRegister(user);
    }

    /**
     * 获取用户信息
     * @return
     * @throws ServiceException
     */
    @GetMapping("/info")
    public ResponseVO<UserInfo> userInfo(@RequestHeader("token") String token) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        return userService.userInfo(userId);
    }

    /**
     * 给予用户角色
     * @param user
     * @return
     * @throws ServiceException
     */
    @PostMapping("/authorize")
    public ResponseVO<Map<String, Object>> addUserRole(@RequestBody UserRoleRelations relations,
                                                       @RequestHeader("token") String token) throws ServiceException {
        return userService.addUserRole(relations, token);
    }

    /**
     * 移除用户角色
     * @param relations
     * @param token
     * @return
     * @throws ServiceException
     */
    @PostMapping("/revoke-authorize")
    public ResponseVO<Map<String, Object>> removeUserRole(@RequestBody UserRoleRelations relations,
                                                          @RequestHeader("token") String token) throws ServiceException {
        return userService.removeUserRole(relations, token);
    }

    /**
     * 修改用户信息
     * @param token
     * @param user
     * @return
     * @throws ServiceException
     */
    @PutMapping("/info")
    public ResponseVO<Map<String, Object>> modifyUserInfo(@RequestHeader("token") String token,
                                                          @RequestBody User user) throws ServiceException {
        return userService.modifyUserInfo(token, user);
    }

    /**
     * 获取所有账号
     * @param token
     * @param user
     * @return
     * @throws ServiceException
     */
    @PostMapping("/info/list")
    public ResponseVO<List<UserInfo>> getAllUserInfo(@RequestHeader("token") String token,
                                                     @RequestBody User user) throws ServiceException {
        return userService.getAllUserInfo(token, user);
    }

    /**
     * 删除账号
     * @param token
     * @param user
     * @return
     * @throws ServiceException
     */
    @PostMapping("/delete-user")
    public ResponseVO<Map<String, Object>> deleteUser(@RequestHeader("token") String token,
                                                     @RequestBody User user) throws ServiceException {
        return userService.deleteUser(token, user);
    }

    /**
     * 增加用户
     * @param user
     * @param token
     * @return
     * @throws ServiceException
     */
    @PostMapping("/add")
    public ResponseVO<Map<String, Object>> addUser(@RequestBody User user,
                                                   @RequestHeader("token") String token) throws ServiceException {
        return userService.addUser(token, user);
    }

    /**
     * 一键修改用户密码
     * @param token
     * @param user
     * @return
     * @throws ServiceException
     */
    @PostMapping("/info/password")
    public ResponseVO<Map<String, Object>> modifyPassword(@RequestHeader("token") String token,
                                                          @RequestBody User user) throws ServiceException {
        return userService.modifyPassword(token, user);
    }

    /**
     * 获取所有部门
     * @param token
     * @return
     * @throws ServiceException
     */
    @GetMapping("/all-department")
    public ResponseVO<List<String>> getAllDepartment(@RequestHeader("token") String token) throws ServiceException {
        return userService.getAllDepartment(token);
    }

    /**
     * 获取所有岗位类型
     * @param token
     * @return
     * @throws ServiceException
     */
    @GetMapping("/all-job-position-target")
    public ResponseVO<List<String>> getAllJobPositionTarget(@RequestHeader("token") String token) throws ServiceException {
        return userService.getAllJobPositionTarget(token);
    }

    /**
     * 获取所有人员类型
     * @param token
     * @return
     * @throws ServiceException
     */
    @GetMapping("/all-job-doctor-target")
    public ResponseVO<List<String>> getAllJobDoctorTarget(@RequestHeader("token") String token) throws ServiceException {
        return userService.getAllJobDoctorTarget(token);
    }
}
