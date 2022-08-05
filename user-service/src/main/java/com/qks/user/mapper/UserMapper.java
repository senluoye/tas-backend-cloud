package com.qks.user.mapper;

import com.qks.common.po.Role;
import com.qks.common.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:48
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where login_name = #{loginName} and password = #{password}")
    User getUserByStatus(String loginName, String password);

    @Select("select * from user where id = #{userId}")
    User getUserByStatus(Integer userId);

    @Select("select * " +
            "from role " +
            "where role.id = (" +
            "   select * " +
            "   from user_role_relations " +
            "   where user_id = #{userId}" +
            ")")
    Role[] getUserRolesByStatus(Integer userId);
}
