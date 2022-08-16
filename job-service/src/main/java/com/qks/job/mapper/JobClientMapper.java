package com.qks.job.mapper;

import com.qks.common.po.Job;
import com.qks.common.po.JobNameNode;
import com.qks.common.po.Task;
import com.qks.common.po.UserJobRelations;
import org.apache.ibatis.annotations.*;

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
public interface JobClientMapper {

    @Select("select * from user_job_relations " +
            "where user_id = #{userId} and status <> -4 order by create_at desc limit 1")
    UserJobRelations getUserJobByUserId(Integer userId);
}
