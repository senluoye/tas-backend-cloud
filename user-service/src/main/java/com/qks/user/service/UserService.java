package com.qks.user.service;

import com.qks.common.exception.ServiceException;
import com.qks.common.po.User;
import com.qks.common.po.UserRoleRelations;
import com.qks.common.utils.Response;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:39
 */
public interface UserService {
    ResponseVO<Map<String, Object>> userLogin(User user) throws ServiceException;
    ResponseVO<UserInfo> userInfo(Integer userId) throws ServiceException;

    ResponseVO<Map<String, Object>> addUserRole(UserRoleRelations relations, String token) throws ServiceException;

    ResponseVO<Map<String, Object>> removeUserRole(UserRoleRelations relations, String token) throws ServiceException;

    ResponseVO<Map<String, Object>> modifyUserInfo(String token, User user) throws ServiceException;

    ResponseVO<List<UserInfo>> getAllUserInfo(String token, User user) throws ServiceException;

    ResponseVO<Map<String, Object>> deleteUser(String token, User user) throws ServiceException;

    ResponseVO<Map<String, Object>> addUser(String token, User user) throws ServiceException;

    ResponseVO<List<String>> getAllDepartment(String token);

    ResponseVO<Map<String, Object>> modifyPassword(String token, User user) throws ServiceException;

    ResponseVO<List<String>> getAllJobPositionTarget(String token);

    ResponseVO<List<String>> getAllJobDoctorTarget(String token);
}
