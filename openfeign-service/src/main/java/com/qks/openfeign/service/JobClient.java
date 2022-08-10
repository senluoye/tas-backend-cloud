package com.qks.openfeign.service;

import com.qks.common.po.Job;
import com.qks.common.po.UserJobRelations;
import com.qks.common.utils.Response;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.UserJobRelationVO;
import com.qks.openfeign.service.backimpl.JobBackImpl;
import org.apache.ibatis.annotations.Delete;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.awt.datatransfer.Clipboard;
import java.time.temporal.UnsupportedTemporalTypeException;
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

    @PostMapping("/api/jobs/teachers")
    List<UserJobRelationVO> getUserJobXML(String type);

    @PostMapping("/api/jobs/teachers/target")
    ResponseVO<UserJobRelations> getUserJob(Integer userId, Integer jobId);

    @DeleteMapping("/api/jobs/teachers/target")
    ResponseVO<Integer> deleteUserJob(Integer id);

    @PostMapping("/api/jobs/teachers/target/s")
    ResponseVO<List<UserJobRelations>> getUserJobs(Integer id, Integer[] allys);

    @PostMapping("/api/jobs")
    ResponseVO<Job> getJob(Integer jobId);

    @PostMapping("/api/jobs/teachers/target/userId")
    ResponseVO<UserJobRelations> getUserJobByUserId(Integer userId);

    @PostMapping("/api/jobs/teachers/target/id")
    ResponseVO<UserJobRelations> getUserJobById(Integer id);

    @PutMapping("/api/jobs/teachers/target")
    ResponseVO<Integer> updateUserJobStatus(Integer id, Integer status);
}
