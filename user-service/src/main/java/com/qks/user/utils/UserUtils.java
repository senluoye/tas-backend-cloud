package com.qks.user.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.qks.common.exception.ServiceException;
import com.qks.common.po.UserRoleRelations;
import com.qks.common.utils.JwtUtils;
import com.qks.user.mapper.UserMapper;
import lombok.Data;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-08 15:30
 */
@Component
public class UserUtils {

    @Resource
    private UserMapper userMapper;

    @Resource
    private Jedis jedis;

    public boolean isAdminOrLeadership(Integer userId) {
        UserRoleRelations relations = userMapper.getUserRole(userId);
        return relations.getUserId() == 0 && relations.getRoleId() == 0 || relations.getRoleId() > 2;
    }

    /**
     * 查看是否是最新登陆
     * 如果是，就返回 true
     * 否则返回 false
     *
     * @param token
     * @return
     */
    public boolean checkLogin(String nowToken) throws ServiceException {
        Integer userId;
        try {
            userId = Integer.valueOf(JwtUtils.parser(nowToken).get("userId").toString());
            if (userId.equals(0))
                return false;
        } catch (Exception e) {
            return false;
        }

        String oldToken = jedis.get(userId.toString());
        return oldToken != null && !oldToken.equals("") && oldToken.equals(nowToken);
    }
}
