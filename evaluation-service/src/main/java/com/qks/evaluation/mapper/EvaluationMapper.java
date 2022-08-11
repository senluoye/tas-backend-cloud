package com.qks.evaluation.mapper;

import com.qks.common.po.*;
import org.apache.ibatis.annotations.*;

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
