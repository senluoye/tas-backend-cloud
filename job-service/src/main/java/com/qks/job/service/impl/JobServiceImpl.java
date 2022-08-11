package com.qks.job.service.impl;

import com.qks.common.dto.evaluation.DeleteEvaluationDTO;
import com.qks.common.dto.evaluation.EvaluateDTO;
import com.qks.common.dto.evaluation.TeacherDTO;
import com.qks.common.dto.evaluation.TeacherListDTO;
import com.qks.common.exception.ServiceException;
import com.qks.common.po.*;
import com.qks.common.utils.CollectionUtils;
import com.qks.common.utils.JwtUtils;
import com.qks.common.utils.Response;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.SelfJobVO;
import com.qks.job.mapper.JobMapper;
import com.qks.job.service.JobService;
import com.qks.job.utils.JobUtil;
import com.qks.openfeign.service.JobClient;
import com.qks.openfeign.service.UserClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:46
 */
@Service
public class JobServiceImpl implements JobService {

    @Resource
    private JobMapper jobMapper;

    @Resource
    private UserClient userClient;

    @Resource
    private JobUtil util;


    @Override
    public ResponseVO<Job> createJob(String token, Job job) throws ServiceException {
        Integer userId = Integer.parseInt(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        if (job.getRank() == 0 || "".equals(job.getJobType())
                || "".equals(job.getJobLevelName()) || "".equals(job.getDuring())) {
            throw new ServiceException("参数有误");
        }

        Job newJob = Job.builder()
                .Rank(job.getRank())
                .jobType(job.getJobType())
                .jobLevelName(job.getJobLevelName())
                .jobPositionTarget(job.getJobPositionTarget())
                .jobDoctorTarget(job.getJobDoctorTarget())
                .during(job.getDuring())
                .build();
        String applyTaskName = "聘用" + job.getJobType() + job.getJobLevelName() + job.getRank() + job.getJobPositionTarget() + job.getJobDoctorTarget();
        Task applyTask = Task.builder()
                .name(applyTaskName)
                .description("申请该岗位的任务合集")
                .build();
        if (jobMapper.addTask(applyTask) < 1) {
            throw new ServiceException("添加任务失败");
        }
        return null;
    }

    @Override
    public ResponseVO<Integer> deleteJob(String token, Integer id) {
        return null;
    }

    @Override
    public ResponseVO<List<Job>> getJobList(String token, Map<String, String> queryMap) {
        return null;
    }

    @Override
    public ResponseVO<Map<String, Object>> modifyJobInfo(String token, Job job) {
        return null;
    }

    @Override
    public ResponseVO<Map<String, Object>> applyJob(String token, UserJobRelations relations) {
        return null;
    }

    @Override
    public ResponseVO<List<JobNameNode>> selectJobNameNode(Integer id) {
        return null;
    }

    @Override
    public ResponseVO<SelfJobVO> getSelfJob(String token, Integer status) {
        return null;
    }

    @Override
    public ResponseVO<Map<String, Object>> updateProgress(String token, UserJobRelations relations) {
        return null;
    }

    @Override
    public ResponseVO<Job> copyJob(String token, Integer id) {
        return null;
    }
}
