package com.qks.job.controller;

import com.qks.common.dto.evaluation.DeleteEvaluationDTO;
import com.qks.common.dto.evaluation.EvaluateDTO;
import com.qks.common.dto.evaluation.TeacherDTO;
import com.qks.common.dto.evaluation.TeacherListDTO;
import com.qks.common.exception.ServiceException;
import com.qks.common.po.*;
import com.qks.common.utils.Response;
import com.qks.common.vo.*;
import com.qks.job.mapper.JobMapper;
import com.qks.job.service.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:36
 */
@RestController
@RequestMapping("/api/job")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * 创建一个岗位
     * @param token
     * @param job
     * @return
     */
    @PostMapping("/create")
    public ResponseVO<Job> createJob(@RequestHeader("token") String token,
                                  @RequestBody Job job) {
        return jobService.createJob(token, job);
    }

    /**
     * 删除岗位
     * @param token
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public ResponseVO<Integer> deleteJob(@RequestHeader("token") String token,
                                  @RequestBody Integer id) {
        return jobService.deleteJob(token, id);
    }

    /**
     * 搜索岗位列表
     * @param token
     * @param jobType
     * @param rank
     * @param jobLevelName
     * @param jobPositionTarget
     * @param jobDoctorTarget
     * @param start
     * @param end
     * @param canApply
     * @return
     */
    @GetMapping("/list")
    public ResponseVO<List<Job>> getJobList(@RequestHeader("token") String token,
                                            @RequestParam("jobType") String jobType,
                                            @RequestParam("rank") String rank,
                                            @RequestParam("jobLevelName") String jobLevelName,
                                            @RequestParam("jobPositionTarget") String jobPositionTarget,
                                            @RequestParam("jobDoctorTarget") String jobDoctorTarget,
                                            @RequestParam("start") String start,
                                            @RequestParam("end") String end,
                                            @RequestParam("canApply") String canApply) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("jobType", jobType);
        queryMap.put("rank", rank);
        queryMap.put("jobLevelName", jobLevelName);
        queryMap.put("jobPositionTarget", jobPositionTarget);
        queryMap.put("jobDoctorTarget", jobDoctorTarget);
        queryMap.put("start", start);
        queryMap.put("end", end);
        queryMap.put("canApply", canApply);
        return jobService.getJobList(token, queryMap);
    }

    /**
     * 修改岗位信息
     * @param token
     * @param job
     * @return
     */
    @PutMapping("/info")
    public ResponseVO<Map<String, Object>> modifyJobInfo(@RequestHeader("token") String token, @RequestBody Job job) {
        return jobService.modifyJobInfo(token, job);
    }

    /**
     * 教师申请岗位
     * @param token
     * @param relations
     * @return
     */
    @PostMapping("/apply")
    public ResponseVO<Map<String, Object>> applyJob(@RequestHeader("token") String token, @RequestBody UserJobRelations relations) {
        return jobService.applyJob(token, relations);
    }

    /**
     * 获取下拉框的岗位树
     * @param id
     * @return
     */
    @GetMapping("/get-select-job-name-node-list")
    public ResponseVO<List<JobNameNode>> selectJobNameNode(@RequestParam("id") Integer id) {
        return jobService.selectJobNameNode(id);
    }

    /**
     * 获取个人岗位列表
     * @param token
     * @param status
     * @return
     */
    @GetMapping("/self")
    public ResponseVO<SelfJobVO> getSelfJob(@RequestHeader("token") String token,
                                            @RequestParam("status") Integer status) {
        return jobService.getSelfJob(token, status);
    }

    /**
     * 更新岗位进度
     * @param token
     * @param relations
     * @return
     */
    @PutMapping("/update-progress")
    public ResponseVO<Map<String, Object>> updateProgress(@RequestHeader("token") String token,
                                       @RequestBody UserJobRelations relations) {
        return jobService.updateProgress(token, relations);
    }

    /**
     * 复制岗位
     * @param token
     * @param id
     * @return
     */
    @PostMapping("/copy")
    public ResponseVO<Job> copyJob(@RequestHeader("token") String token,
                                @RequestBody Integer id) {
        return jobService.copyJob(token, id);
    }
}
