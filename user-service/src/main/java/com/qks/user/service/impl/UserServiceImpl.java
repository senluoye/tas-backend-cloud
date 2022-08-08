package com.qks.user.service.impl;

import com.qks.common.exception.ServiceException;
import com.qks.common.po.*;
import com.qks.common.utils.ComputeUtil;
import com.qks.common.utils.JwtUtils;
import com.qks.common.utils.Response;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.UserInfo;
import com.qks.openfeign.service.EvaluationClient;
import com.qks.openfeign.service.JobClient;
import com.qks.user.mapper.UserMapper;
import com.qks.user.service.UserService;
import com.qks.user.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
    private final UserUtils utils;

    @Resource
    private EvaluationClient evaluationClient;

    @Resource
    private JobClient jobClient;

    public UserServiceImpl(UserMapper userMapper, UserUtils utils) {
        this.userMapper = userMapper;
        this.utils = utils;
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

        List<Role> roles = userMapper.getRolesByStatus(userId);
        UserJobRelations relations = userMapper.getUserJobRelations(userId);
        Job job = userMapper.getJobByStatus(relations.getJobId());

        return Response.success(UserInfo.builder()
                .id(user.getId())
                .loginName(user.getLoginName())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .department(user.getDepartment())
                .roles(roles)
                .jobs(job)
                .build());
    }

    @Override
    public ResponseVO<Map<String, Object>> addUserRole(UserRoleRelations relations, String token) throws ServiceException {
        if (relations.getUserId() == 0 || relations.getRoleId() == 0) {
            throw new ServiceException("用户id或角色id不能为空");
        }

        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        UserRoleRelations userRole = userMapper.getUserRole(userId);
        if (userRole.getUserId() == 0 && userRole.getRoleId() == 0 || (userRole.getRoleId() > relations.getRoleId())) {
            throw new ServiceException("没有权限");
        }

        if (userMapper.addUserRole(relations) == 0) {
            throw new ServiceException("添加失败");
        }
        return Response.success(null);
    }

    @Override
    public ResponseVO<Map<String, Object>> removeUserRole(UserRoleRelations relations, String token) throws ServiceException {
        if (relations.getRoleId() == 0 || relations.getUserId() == 0) {
            throw new ServiceException("数据不能为空");
        }

        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        UserRoleRelations userRole = userMapper.getUserRole(userId);
        if (userRole.getUserId() == 0 && userRole.getRoleId() == 0 || (userRole.getRoleId() > relations.getRoleId())) {
            throw new ServiceException("没有权限");
        }

        if (userMapper.deleteUserRole(relations) == 0) {
            throw new ServiceException("删除失败");
        }
        return Response.success(null);
    }

    @Override
    public ResponseVO<Map<String, Object>> modifyUserInfo(String token, User user) throws ServiceException {
        if (user.getLoginName() == "" || user.getPassword() == "") {
            throw new ServiceException("数据不能为空");
        }

        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        String loginName = user.getLoginName();
        String password = ComputeUtil.decrypt(user.getPassword());
        User targetUser = userMapper.getUserByStatus(loginName, password);

        if (!targetUser.getId().equals(userId)) {
            UserRoleRelations currentUserRole = userMapper.getUserRole(userId);
            UserRoleRelations targetUserRole = userMapper.getUserRole(targetUser.getId());

            if ((currentUserRole.getUserId() == 0 && currentUserRole.getRoleId() == 0) || currentUserRole.getRoleId() > 2 || targetUserRole.getRoleId() == 1) {
                throw new ServiceException("没有权限");
            }
        }

        if (user.getPassword() != "") {
            user.setPassword(ComputeUtil.encrypt(user.getPassword()));
        }

        if (userMapper.updateUser(user) < 1) {
            throw new ServiceException("更新失败");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        return Response.success(data);
    }

    @Override
    public ResponseVO<List<UserInfo>> getAllUserInfo(String token, User user) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        UserRoleRelations relations = userMapper.getUserRole(userId);
        if (relations.getUserId() == 0 && relations.getRoleId() == 0 || relations.getRoleId() > 2) {
            throw new ServiceException("没有权限");
        }
        List<User> users;
        if ("".equals(user.getLoginName()) && "".equals(user.getName())) {
            users = userMapper.getUsers();
        } else {
            User tempDa = User.builder().build();
            users = userMapper.getUsersByXML(user.getLoginName(), user.getName());
        }

        List<UserInfo> data = new ArrayList<>();
        for (User now : users) {
            data.add(UserInfo.builder()
                    .id(now.getId())
                    .loginName(now.getLoginName())
                    .name(now.getName())
                    .email(now.getEmail())
                    .phone(now.getPhone())
                    .department(now.getDepartment())
                    .roles(userMapper.getRoles(now.getId()))
                    .build());
        }

        return Response.success(data);
    }

    @Override
    public ResponseVO<Map<String, Object>> deleteUser(String token, User user) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        UserRoleRelations currentUserRole = userMapper.getUserRole(userId);
        UserRoleRelations targetUserRole = userMapper.getUserRole(user.getId());

        if (currentUserRole.getUserId() == 0 && currentUserRole.getRoleId() == 0 ||
                currentUserRole.getRoleId() > 2 ||
                targetUserRole.getRoleId() != 0 && currentUserRole.getRoleId() >= targetUserRole.getUserId()) {
            throw new ServiceException("没有权限");
        }

        List<UserJobRelations> userJobRelations = userMapper.getUserJob(user.getId());
        evaluationClient.deleteEvaluation(user.getId());
        for (UserJobRelations relation : userJobRelations) {
            userMapper.deleteUserJob(relation.getId());
        }
        userMapper.deleteUserRoleByUserId(user.getId());
        if (userMapper.deleteUser(user.getId()) < 1) {
            throw new ServiceException("删除失败");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        return Response.success(data);
    }

    @Override
    public ResponseVO<Map<String, Object>> addUser(String token, User user) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!utils.isAdminOrLeadership(userId)) {
            throw new ServiceException("没有权限");
        }

        User t = userMapper.getUserByStatus(user.getLoginName());
        if (t != null) {
            throw new ServiceException("用户已存在");
        }

        user.setPassword(ComputeUtil.encrypt(user.getPassword()));
        if (userMapper.addUsersXML(user) < 1) {
            throw new ServiceException("添加失败");
        }
        UserRoleRelations relations = UserRoleRelations.builder()
                .userId(user.getId())
                .roleId(4)
                .build();
        if (userMapper.addUserRole(relations) < 1) {
            throw new ServiceException("添加失败");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getId());
        return Response.success(data);
    }

    @Override
    public ResponseVO<List<String>> getAllDepartment(String token) {
        return Response.success(userMapper.getDepartments());
    }

    @Override
    public ResponseVO<Map<String, Object>> modifyPassword(String token, User user) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!utils.isAdminOrLeadership(userId)) {
            throw new ServiceException("没有权限");
        }

        if ("".equals(user.getLoginName())) {
            throw new ServiceException("传输数据不能为空");
        }

        if (userMapper.resetUserPassword(user.getLoginName(), ComputeUtil.encrypt("123456")) < 1) {
            throw new ServiceException("重置失败");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("loginName", user.getLoginName());
        return Response.success(data);
    }

    @Override
    public ResponseVO<List<String>> getAllJobPositionTarget(String token) {
        return jobClient.getJobs();
    }

    @Override
    public ResponseVO<List<String>> getAllJobDoctorTarget(String token) {
        return jobClient.getAllJobDoctorTarget();
    }

    public ResponseVO<Boolean> IsAdminOrLeadership(Integer userId) {
        return Response.success(utils.isAdminOrLeadership(userId));
    }
}
