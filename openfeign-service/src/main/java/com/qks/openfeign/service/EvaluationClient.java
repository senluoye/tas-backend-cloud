package com.qks.openfeign.service;

import com.qks.openfeign.service.backimpl.JobBackImpl;
import org.apache.ibatis.annotations.Delete;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-08 15:09
 */
@FeignClient(value = "evaluationservice", fallback = JobBackImpl.class)
public interface EvaluationClient {
    @DeleteMapping("/api/evaluation")
    Integer deleteEvaluation(Integer userId);
}
