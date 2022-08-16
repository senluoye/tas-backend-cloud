package com.qks.openfeign.service;

import com.qks.common.po.Job;
import com.qks.common.po.UserJobRelations;
import com.qks.common.utils.Response;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.UserJobRelationVO;
import com.qks.openfeign.config.MyFeignRequestInterceptor;
import com.qks.openfeign.service.backimpl.JobBackImpl;
import org.apache.ibatis.annotations.Delete;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
@FeignClient(value = "jobservice", fallbackFactory = JobBackImpl.class, configuration = MyFeignRequestInterceptor.class)
public interface JobClient {

    @GetMapping("/api/job")
    ResponseVO<List<String>> getJobs();

    @GetMapping("/api/job/target")
    ResponseVO<List<String>> getAllJobDoctorTarget();

    @PostMapping("/api/job/teachers")
    List<UserJobRelationVO> getUserJobXML(String type);

    @PostMapping("/api/job/teachers/target")
    ResponseVO<UserJobRelations> getUserJob(@RequestParam("userId") Integer userId,
                                            @RequestParam("jobId") Integer jobId);

    @DeleteMapping("/api/job/teachers/target")
    ResponseVO<Integer> deleteUserJob(Integer id);

    @PostMapping("/api/job/teachers/target/s")
    ResponseVO<List<UserJobRelations>> getUserJobs(@RequestParam("id") Integer id,
                                                   @RequestParam("allys") Integer[] allys);

    @PostMapping("/api/job")
    ResponseVO<Job> getJob(Integer jobId);

    @PostMapping("/api/job/teachers/target/userId")
    ResponseVO<UserJobRelations> getUserJobByUserId(Integer userId);

    @PostMapping("/api/job/teachers/target/id")
    ResponseVO<UserJobRelations> getUserJobById(Integer id);

    @PutMapping("/api/job/teachers/target")
    ResponseVO<Integer> updateUserJobStatus(@RequestParam("id") Integer id,
                                            @RequestParam("status") Integer status);
}
