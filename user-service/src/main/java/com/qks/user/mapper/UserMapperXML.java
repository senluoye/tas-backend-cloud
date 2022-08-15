package com.qks.user.mapper;

import com.qks.common.po.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-08 18:16
 */
@Mapper
public interface UserMapperXML {
    int addUsersXML(User user);

    List<User> getUsersByXML(String loginName, String name);

}
