package com.qks.job.mapper;

import com.qks.common.po.Job;
import com.qks.common.po.JobNameNode;
import com.qks.common.po.Task;
import com.qks.common.po.UserJobRelations;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Map;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:48
 */
@Mapper
public interface JobMapper {

    @Insert("insert into tasks(name, description, id) VALUES (#{name}, #{description}, #{id})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    boolean addTask(Task applyTask);

    @Insert("insert into jobs(`rank`, job_type, job_level_name, job_position_target, job_doctor_target, during, id) VALUES (#{rank}, #{jobType}, #{jobLevelName}, #{jobPositionTarget}, #{jobDoctorTarget}, #{during}, #{id})")
    boolean addJob(Job job);

    @Select("select * from job_name_nodes where name = #{name} and parent_id = #{parentId}")
    JobNameNode getJobNameNode(String name, Integer parentId);

    @Insert("insert into job_name_nodes(name, parent_id, cnt) VALUES (#{name}, #{parentId}, #{cnt})")
    boolean createJobNameNode(JobNameNode jobNameNode);

    @Select("select * from jobs where id = #{id}")
    Job getJob(Integer id);

    @Delete("delete from jobs where id = #{id}")
    boolean deleteJob(Integer id);

    @Delete("delete from job_name_nodes where id = #{id}")
    boolean deleteJobNameNode(Integer id);

    @Update("update job_name_nodes set cnt = #{cnt} where id = #{id}")
    boolean updateJobNameNodeCnt(JobNameNode node);

    List<Job> getJobsXML(Map<String, String> queryMap);

    @Update("update jobs set `rank` = #{rank}, job_type = #{jobType}, job_level_name = #{jobLevelName}, " +
            "job_position_target = #{jobPositionTarget}, during = #{during}, status = #{status} " +
            "where id = #{id}")
    boolean updateJob(Job job);

    @Select("select * from jobs where id = #{jobId} and status = #{status}")
    Job getJobByStatus(Integer jobId, Integer status);

    @Select("select * from user_job_relations where user_id = #{userId} and status <> -4 " +
            "order by create_at desc limit 1")
    UserJobRelations getUserJobByUserId(Integer userId);

    @Insert("insert into user_job_relations (status, user_id, job_id) values (#{status}, #{userId}, #{jobId}})")
    boolean addUserJob(UserJobRelations relations);

    @Select("select * from job_name_nodes  where parent_id = #{id}")
    List<JobNameNode> getJobNameNodeByParentId(Integer id);

    @Select("select * from jobs where id = (select job_id from user_job_relations where user_id = #{id})")
    List<Job> getJobByUserId(Integer userId);

    @Select("select * from user_job_relations where user_id = #{userId}")
    List<UserJobRelations> getUserJobsByUserId(Integer userId);

    @Select("select * from user_job_relations where id = #{id}")
    UserJobRelations getUserJob(Integer id);

    @Update("update user_job_relations set status = #{status} where id = #{id}")
    boolean updateUserJob(UserJobRelations relations);
}
