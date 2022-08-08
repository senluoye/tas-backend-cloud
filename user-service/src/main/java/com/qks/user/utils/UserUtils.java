package com.qks.user.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.qks.common.po.UserRoleRelations;
import com.qks.user.mapper.UserMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

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

    public boolean isAdminOrLeadership(Integer userId) {
        UserRoleRelations relations = userMapper.getUserRole(userId);
        return relations.getUserId() == 0 && relations.getRoleId() == 0 || relations.getRoleId() > 2;
    }
}
