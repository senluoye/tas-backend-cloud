package com.qks.user.service;

import com.qks.common.exception.ServiceException;
import com.qks.common.po.User;
import com.qks.common.utils.Response;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.UserInfo;

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
}
