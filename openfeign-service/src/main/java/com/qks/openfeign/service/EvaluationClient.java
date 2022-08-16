package com.qks.openfeign.service;

import com.qks.common.po.Evaluation;
import com.qks.common.utils.Response;
import com.qks.common.vo.ResponseVO;
import com.qks.openfeign.config.MyFeignRequestInterceptor;
import com.qks.openfeign.service.backimpl.JobBackImpl;
import org.apache.ibatis.annotations.Delete;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-08 15:09
 */
@FeignClient(value = "evaluationservice", fallbackFactory = JobBackImpl.class, configuration = MyFeignRequestInterceptor.class)
public interface EvaluationClient {
    @DeleteMapping("/api/evaluation")
    Integer deleteEvaluation(Integer userId);

    @PostMapping("/api/evaluation/during")
    List<Evaluation> getDuringEvaluationXML(Integer status);

    @PutMapping("/api/evaluation/apply")
    ResponseVO<Boolean> updateApplyUserJob(@RequestParam("id") Integer id, @RequestParam("status") int status);


    @PutMapping("/api/evaluation/apply/1")
    ResponseVO<Boolean> updateEvaluationUserJob(@RequestParam("id") Integer id, @RequestParam("i") int i);

    @PutMapping("/api/evaluation")
    ResponseVO<Boolean> updateUserJob(Integer id);
}
