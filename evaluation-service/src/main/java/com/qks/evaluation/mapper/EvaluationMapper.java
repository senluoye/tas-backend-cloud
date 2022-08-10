package com.qks.evaluation.mapper;

import com.qks.common.po.*;
import org.apache.ibatis.annotations.*;
import org.checkerframework.checker.index.qual.SameLen;
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
public interface EvaluationMapper {
    @Select("select * from users where login_name = #{loginName} and password = #{password}")
    User getUserByStatus(String loginName, String password);

    @Select("select * from users where id = #{userId} limit 1")
    User getUserById(Integer userId);
    
    @Select("select * from users where login_name = #{loginName} limit 1")
    User getUserByLoginName(String loginName);

    @Select("select * " +
            "from roles " +
            "where roles.id = (" +
            "   select * " +
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

    @Insert("insert into evaluation (user_job_relation_id, expert_id, status) values " +
            "(#{userJobRelationsId}, #{expertId}, #{status})")
    void addEvaluation(Evaluation evaluation);

    @Select("select * " +
            "from evaluation " +
            "where user_job_relation_id = #{id} " +
            "and expert_id = #{expertId} " +
            "order by create_at limit 1")
    Evaluation getMyEvaluation(Integer id, Integer expertId);

    @Select("select * from evaluation where user_job_relation_id = #{id} and abs(status) < 10")
    List<Evaluation> getFirstDuringEvaluationXML(Integer id);

    @Select("select * from evaluation where user_job_relation_id = #{id} and abs(status) > 10")
    List<Evaluation> getSecondDuringEvaluationXML(Integer id);

    @Select("select * from evaluation where expert_id = #{userId}")
    List<Evaluation> getEvaluation(Integer userId);

    @Delete("delete from evaluation where id = #{id} and expert_id = #{expertId}")
    void deleteEvaluation(Integer id, Integer expertId);

    @Update("update evaluation set status = #{status} where id = #{id}")
    Integer updateStatus(Integer status, Integer id);

    @Select("select * from evaluation where id in(" +
            "   select max(id) from evaluation where user_job_relation_id = #{id} group by expert_id" +
            ")")
    List<Evaluation> getTeacherEvaluationsByUserJobId(Integer id);

    @Select("select * from evaluation where user_job_relation_id = #{id}")
    List<Evaluation> getEvaluationsByUserJobId(Integer id);

    @Select("select * from evaluation where id = #{id}")
    Evaluation getEvaluationById(Integer id);

    @Update("update evaluation set status = #{status}, comment = #{comment} where id = #{id}")
    boolean updateEvaluation(Evaluation preEvaluation);
}
