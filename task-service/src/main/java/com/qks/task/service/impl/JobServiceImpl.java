package com.qks.task.service.impl;

import com.qks.common.exception.ServiceException;
import com.qks.common.utils.JwtUtils;
import com.qks.common.utils.Response;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.SelfJobVO;
import com.qks.common.vo.UserInfo;
import com.qks.task.mapper.JobMapper;
import com.qks.task.service.JobService;
import com.qks.task.utils.JobUtil;
import com.qks.openfeign.service.TaskClient;
import com.qks.openfeign.service.UserClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Resource
    private TaskClient taskClient;


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

        String applyTaskName = "聘用:" + job.getJobType() + job.getJobLevelName() +
                job.getRank() + job.getJobPositionTarget() + job.getJobDoctorTarget();
        Task applyTask = Task.builder()
                .name(applyTaskName)
                .description("申请该岗位的任务合集")
                .build();
        if (!jobMapper.addTask(applyTask)) {
            throw new ServiceException("添加任务失败");
        }

        String appraisalTaskName = "评审:" + job.getJobType() + job.getJobLevelName() +
                job.getRank() + job.getJobPositionTarget() + job.getJobDoctorTarget();
        Task appraisalTask = Task.builder()
                .name(appraisalTaskName)
                .description("评审该岗位的任务集合")
                .build();
        if (!jobMapper.addTask(appraisalTask)) {
            throw new ServiceException("添加任务失败");
        }

        job.setApplyTaskID(applyTask.getId());
        job.setAppraisalTaskID(appraisalTask.getId());
        if (!jobMapper.addJob(job)) {
            throw new ServiceException("添加岗位失败");
        }
        util.createJobNameNode(job);

        return Response.success(job);
    }

    @Override
    public ResponseVO<Integer> deleteJob(String token, Integer id) throws ServiceException {
        Integer userId = Integer.parseInt(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }
        Job existJob = jobMapper.getJob(id);
        if (existJob == null) {
            throw new ServiceException("岗位不存在");
        }
        if (!jobMapper.deleteJob(existJob.getId())) {
            throw new ServiceException("删除岗位失败");
        }
        util.PruneJobNameNode(existJob);

        return Response.success(null);
    }

    @Override
    public ResponseVO<List<Job>> getJobList(String token, Map<String, String> queryMap) {
        List<Job> jobs;
        if (!"true".equals(queryMap.get("canApply"))) {
            queryMap.put("status", String.valueOf(1));
        }
        jobs = jobMapper.getJobsXML(queryMap);
        String start = queryMap.get("start");
        String end = queryMap.get("end");
        if (!"".equals(start) && !"".equals(end)) {
            return Response.success(util.filterJobByDuring(jobs, start, end));
        }
        return Response.success(jobs);
    }

    @Override
    public ResponseVO<Map<String, Object>> modifyJobInfo(String token, Job job) throws ServiceException {
        Integer userId = Integer.parseInt(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        Job existJob = jobMapper.getJob(job.getId());
        if (existJob == null) {
            throw new ServiceException("岗位不存在");
        }

        if (!jobMapper.updateJob(job)) {
            throw new ServiceException("更新失败");
        }
        util.createJobNameNode(job);

        return Response.success(null);
    }

    @Override
    public ResponseVO<Map<String, Object>> applyJob(String token, UserJobRelations relations) throws ServiceException {
        Integer userId = Integer.parseInt(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isTeacher(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        Job existJob = jobMapper.getJobByStatus(relations.getJobId(), 1);
        if (existJob == null) {
            throw new ServiceException("岗位不存在");
        }

        UserJobRelations userJobRel = jobMapper.getUserJobByUserId(userId);
        if (userJobRel != null &&
                (userJobRel.getStatus() == 0 ||
                        (userJobRel.getStatus() != 13 && userJobRel.getStatus() != -4))) {
            throw new ServiceException("有正在申请的岗位");
        }

        if (!jobMapper.addUserJob(relations)) {
            throw new ServiceException("申请失败");
        }

        return Response.success(null);
    }

    @Override
    public ResponseVO<List<JobNameNode>> selectJobNameNode(Integer id) {
        List<JobNameNode> jobNameNodes = jobMapper.getJobNameNodeByParentId(id);
        return Response.success(jobNameNodes);
    }

    @Override
    public ResponseVO<List<SelfJobVO>> getSelfJob(String token, Integer status) throws ServiceException {
        Integer userId = Integer.parseInt(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        User user = userClient.getUser(userId).getData();
        UserInfo userInfo = UserInfo.builder()
                .id(user.getId())
                .loginName(user.getLoginName())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .department(user.getDepartment())
                .jobs(jobMapper.getJobByUserId(userId))
                .build();

        List<UserJobRelations> relations = jobMapper.getUserJobsByUserId(userId);
        List<Job> jobs = userInfo.getJobs();
        List<SelfJobVO> data = new ArrayList<>();
        for (Job job : jobs) {
            for (UserJobRelations relation : relations) {
                if (job.getId().equals(relation.getJobId())) {
                    job.setStatus(relation.getStatus());
                    if (status == 0x3f3f3f3f || Objects.equals(job.getStatus(), status)) {
                        data.add(SelfJobVO.builder()
                                .job(job)
                                .relationId(relation.getId())
                                .build());
                    }
                    relation.setJobId(0x3f3f3f3f);
                    break;
                }
            }
        }

        return Response.success(data);
    }

    @Override
    public ResponseVO<Map<String, Object>> updateProgress(String token, UserJobRelations relation) throws ServiceException {
        Integer userId = Integer.parseInt(JwtUtils.parser(token).get("userId").toString());

        UserJobRelations relations = jobMapper.getUserJob(relation.getId());
        if (relations == null) {
            throw new ServiceException("岗位不存在");
        }

        boolean vaild = false, update = false,  delete = false;
        if (userId.equals(relations.getUserId())) {
            Integer s = relations.getStatus();
            Integer ss = relation.getStatus();
            if (s == 0 && ss == 1) {
                vaild = true;
            } else if (s == 0 && ss == -4) {
                vaild = true;
            } else if (s == 3 && ss == 11) {
                delete = true;
                vaild = true;
            } else if (s == -2 && ss == 1) {
                update = true;
                vaild = true;
            } else if (s == -2 && ss == -4) {
                vaild = true;
            } else if (s == -3 && ss == 1) {
                update = true;
                vaild = true;
            } else if (s == -3 && ss == -4) {
                vaild = true;
            } else if (s == -12 && ss == 11) {
                vaild = true;
            } else if (s == -13 && ss == 11) {
                update = true;
                vaild = true;
            }
        } else {
            throw new ServiceException("数据错误");
        }

        if (!vaild) {
            throw new ServiceException("数据错误");
        }

        if (update) {
            util.ChangeEvaluationStatus(relations);
        }

        if (delete) {
            util.DeleteOldEvaluation(relation);
        }

        relations.setStatus(relation.getStatus());
        if (!jobMapper.updateUserJob(relations)) {
            throw new ServiceException("更新失败");
        }

        return Response.success(null);
    }

    @Override
    public ResponseVO<Job> copyJob(String token, Integer oldJobId) throws ServiceException {
        Integer userId = Integer.parseInt(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        Job job = jobMapper.getJob(oldJobId);
        if (job == null) {
            throw new ServiceException("岗位不存在");
        }

        job.setId(0);
        job.setJobLevelName(job.getJobLevelName() + "【副本】");
        job.setStatus(0);
        String applyTaskName = "聘用:" + job.getJobType() + job.getJobLevelName() +
                job.getRank() + job.getJobPositionTarget() + job.getJobDoctorTarget();
        Task applyTask = Task.builder()
                .name(applyTaskName)
                .description("申请该岗位的任务集合").build();
        applyTask = taskClient.addTask(applyTask).getData();

        String appraisalTaskName =  "评审:" + job.getJobType() + job.getJobLevelName() +
                job.getRank() + job.getJobPositionTarget() + job.getJobDoctorTarget();
        Task appraisalTask = Task.builder()
                .name(appraisalTaskName)
                .description("评审该岗位的任务集合")
                .build();
        appraisalTask = taskClient.addTask(appraisalTask).getData();

        job.setApplyTaskID(applyTask.getId());
        job.setAppraisalTaskID(appraisalTask.getId());

        util.copyTaskTree(job.getApplyTaskID(), job.getApplyTaskID());
        util.copyTaskTree(job.getAppraisalTaskID(), job.getAppraisalTaskID());

        if (!jobMapper.addJob(job)) {
            throw new ServiceException("复制失败");
        }

        util.createJobNameNode(job);

        return Response.success(job);
    }
}
