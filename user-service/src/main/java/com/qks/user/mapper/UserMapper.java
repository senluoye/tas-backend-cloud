package com.qks.user.mapper;

import com.qks.common.dto.user.UserDTO;
import com.qks.common.po.*;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:48
 */
@Mapper
public interface UserMapper {
    @Select("select * from users where login_name = #{loginName} and password = #{password}")
    UserDTO getUserByStatus(@Param("loginName") String loginName, @Param("password") String password);

    @Select("select * from users where id = #{userId} limit 1")
    UserDTO getUserById(Integer userId);
    
    @Select("select * from users where login_name = #{loginName} limit 1")
    User getUserByLoginName(String loginName);

    @Select("select * " +
            "from roles " +
            "where roles.id in (" +
            "   select user_role_relations.role_id " +
            "   from user_role_relations " +
            "   where user_id = #{userId}" +
            ")")
    List<Role> getRolesByStatus(Integer userId);

    @Select("select * from user_job_relations where user_id = #{userId} and status <> -4 order by create_at desc")
    UserJobRelations getUserJobRelations(Integer userId);

    @Select("select * from jobs where id = #{jobId}")
    Job getJobByStatus(Integer jobId);

    @Select("select * " +
            "from user_role_relations " +
            "where user_id = #{userId} " +
            "order by role_id " +
            "limit 1")
    UserRoleRelations getUserRole(Integer userId);

    @Insert("insert into user_role_relations (user_id, role_id) values (#{userId}, #{roleId})")
    int addUserRole(UserRoleRelations relations);

    @Delete("delete from user_role_relations where role_id = #{roleId} and user_id = #{userId}")
    int deleteUserRole(UserRoleRelations relations);

    @Update("update users set login_name = #{loginName}, password = #{password}, " +
            "name = #{name}, email = #{email}, phone = #{phone}, department = #{department} " +
            "where id = #{id}")
    int updateUser(User user);

    @Select("select * from users")
    List<User> getUsers();

    @Select("select * from roles where id >= #{roleId}")
    List<Role> getRoles(Integer roleId);


    @Select("select * from user_job_relations where user_id = #{userId}")
    List<UserJobRelations> getUserJob(Integer id);

    @Delete("delete from user_job_relations where id = #{id}")
    void deleteUserJob(Integer id);

    @Delete("delete from user_job_relations where user_id = #{userId}")
    void deleteUserRoleByUserId(Integer id);

    @Delete("delete from users where id = #{id}")
    Integer deleteUser(Integer id);

    @Insert("insert into users (login_name, password, name, email, phone, department) VALUES " +
            "(#{loginName}, #{password}, #{name}, #{email}, #{phone}, #{department})")
    Integer addUsers(User user);


    @Select("select distinct department from users")
    List<String> getDepartments();

    @Update("update users set password = #{password} where login_name = #{loginName}")
    int resetUserPassword(String loginName, String password);

    @Insert("insert into users set login_name = #{loginName}, password = #{password}, " +
            "name = #{name}, email = #{email}, phone = #{phone}, department = #{department}")
    int addUser(UserDTO userDTO);
}
