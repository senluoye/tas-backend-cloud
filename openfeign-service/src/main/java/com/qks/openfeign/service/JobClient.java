package com.qks.openfeign.service;

import com.qks.common.utils.Response;
import com.qks.common.vo.ResponseVO;
import com.qks.openfeign.service.backimpl.JobBackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-06 00:08
 */
@FeignClient(value = "jobservice", fallback = JobBackImpl.class)
public interface JobClient {

    @GetMapping("/api/jobs")
    ResponseVO<List<String>> getJobs();

    @GetMapping("/api/jobs/target")
    ResponseVO<List<String>> getAllJobDoctorTarget();
}
