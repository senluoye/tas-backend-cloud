package com.qks.evaluation.service.impl;

import com.qks.common.dto.evaluation.DeleteEvaluationDTO;
import com.qks.common.dto.evaluation.EvaluateDTO;
import com.qks.common.dto.evaluation.TeacherDTO;
import com.qks.common.dto.evaluation.TeacherListDTO;
import com.qks.common.exception.ServiceException;
import com.qks.common.po.Evaluation;
import com.qks.common.po.Job;
import com.qks.common.po.User;
import com.qks.common.po.UserJobRelations;
import com.qks.common.utils.CollectionUtils;
import com.qks.common.utils.JwtUtils;
import com.qks.common.utils.Response;
import com.qks.common.vo.*;
import com.qks.evaluation.mapper.EvaluationMapper;
import com.qks.evaluation.service.EvaluationService;
import com.qks.evaluation.utils.EvaluationUtil;
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
public class EvaluationServiceImpl implements EvaluationService {

    @Resource
    private EvaluationMapper evaluationMapper;

    @Resource
    private UserClient userClient;

    @Resource
    private EvaluationUtil util;

    @Resource
    private JobClient jobClient;

    @Override
    public ResponseVO<List<User>> getEvaluationList(String token, User user) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        String name = user.getName();
        String department = user.getDepartment();

        List<User> expertList = userClient.getExpertListXML(name, department).getData();
        return Response.success(expertList);
    }

    @Override
    public ResponseVO<List<TeacherInfo>> getAppliedTeacher(String token, TeacherDTO teacherDTO) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        List<TeacherInfo> data = new ArrayList<>();
        List<UserJobRelationVO> relations = jobClient.getUserJobXML(teacherDTO.getTypeString());
        for (UserJobRelationVO relation : relations) {
            User user = userClient.getUser(relation.getUserId()).getData();
            if ("".equals(teacherDTO.getName()) && teacherDTO.getName().equals(user.getName())) {
                continue;
            }
            List<Evaluation> evaluations;
            if (Math.abs(relation.getStatus()) < 10) {
                evaluations = evaluationMapper.getFirstDuringEvaluationXML(relation.getId());
            } else {
                evaluations = evaluationMapper.getSecondDuringEvaluationXML(relation.getId());
            }
            relation.setEvaluations(evaluations);

            List<MyEvaluation> myEvaluations = new ArrayList<>();
            for (Evaluation e : evaluations) {
                User expert = userClient.getUser(e.getExpertId()).getData();
                MyEvaluation t = MyEvaluation.builder().build();
                t.setId(e.getId());
                t.setCreateAt(e.getCreateAt());
                t.setExpertId(e.getExpertId());
                t.setExpertName(expert.getName());
                t.setComment(e.getComment());
                t.setStatus(e.getStatus());
                myEvaluations.add(t);
            }

            TeacherInfo data_t = TeacherInfo.builder()
                    .userJobRelationId(relation.getId())
                    .jobId(relation.getJobId())
                    .userId(user.getId())
                    .userName(user.getName())
                    .evaluations(myEvaluations)
                    .createAt(relation.getCreateAt())
                    .build();
            data_t.setType(relation.getStatus() == 1 ? "岗位申请" : relation.getStatus() == 1 ? "岗位考核" : "");

            data.add(data_t);
        }

        return Response.success(data);
    }

    @Override
    public ResponseVO<Map<String, Object>> assignApplyExpertEvaluation(String token, EvaluateDTO evaluateDTO) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("权限不足");
        }
        if (!userClient.isTeacher(userId).getData()) {
            throw new ServiceException("不是老师");
        }

        evaluateDTO.setExpertIdList(CollectionUtils.RemoveRepByMap(evaluateDTO.getExpertIdList()));
        List<Integer> expertIds = evaluateDTO.getExpertIdList();
        for (Integer expertId : expertIds) {
            if (!userClient.isExpert(expertId).getData()) {
                throw new ServiceException("不是专家");
            }
            if (expertId.equals(evaluateDTO.getUserId())) {
                throw new ServiceException("不能审核自己");
            }
        }

        UserJobRelations relations =jobClient.getUserJob(evaluateDTO.getUserId(), evaluateDTO.getJobId()).getData();
        if (relations.getStatus() == 1) {
            if (evaluateDTO.getStatus() != 1) {
                throw new ServiceException("岗位申请未通过");
            }
            jobClient.deleteUserJob(relations.getId());

        } else if (relations.getStatus() == 11) {
            if (evaluateDTO.getStatus() != 1) {
                throw new ServiceException("岗位考核未通过");
            }
            jobClient.deleteUserJob(relations.getId());
        }

        if (evaluateDTO.getExpertIdList().size() == 0) {
            return Response.success(null);
        }

        for (int i = 0; i < evaluateDTO.getExpertIdList().size(); i++) {
            Evaluation evaluation = Evaluation.builder()
                    .userJobRelationsId(relations.getId())
                    .expertId(evaluateDTO.getExpertIdList().get(i))
                    .status(evaluateDTO.getStatus())
                    .build();
            evaluationMapper.addEvaluation(evaluation);
        }

        return Response.success(null);
    }

    @Override
    public ResponseVO<List<AdminTasks>> adminGetTeacherList(String token, TeacherDTO teacherDTO) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        if (teacherDTO.getStatus() > 4 || teacherDTO.getType() > 2) {
            throw new ServiceException("请确认数据");
        }

        Integer[] apply = EvaluationUtil.getApplyLis(teacherDTO.getStatus(), teacherDTO.getType());
        List<User> users = userClient.getUserXML(teacherDTO.getLoginName(), teacherDTO.getName()).getData();
        List<AdminTasks> data = new ArrayList<>();

        for (User user : users) {
            List<UserJobRelations> relations = jobClient.getUserJobs(user.getId(), apply).getData();
            for (UserJobRelations relation : relations) {
                if (relation.getId() == 0) {
                    continue;
                }

                Job job = jobClient.getJob(relation.getJobId()).getData();
                AdminTasks adminTask = AdminTasks.builder()
                        .id(user.getId())
                        .loginName(user.getLoginName())
                        .name(user.getName())
                        .during(job.getDuring())
                        .createAt(relation.getCreateAt())
                        .status(relation.getStatus())
                        .userJobRelationId(relation.getId())
                        .build();

                if (Math.abs(relation.getStatus()) < 10) {
                    adminTask.setTaskId(job.getApplyTaskID());
                } else {
                    adminTask.setTaskId(job.getAppraisalTaskID());
                }

                data.add(adminTask);
            }
        }

        return Response.success(data);
    }

    @Override
    public ResponseVO<List<ExpertTasks>> getTeacherList(String token, TeacherListDTO teacherDTO) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());

        if (!userClient.isExpert(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        List<ExpertTasks> data = new ArrayList<>();
        if ("".equals(teacherDTO.getLoginName()) && "".equals(teacherDTO.getName())) {
            List<Evaluation> evaluations = evaluationMapper.getEvaluation(userId);

            for (Evaluation evaluation : evaluations) {
                UserJobRelations relation = jobClient.getUserJobById(evaluation.getUserJobRelationsId()).getData();
                User user = userClient.getUser(relation.getUserId()).getData();
                Job job = jobClient.getJob(relation.getJobId()).getData();

                ExpertTasks expertTasks = ExpertTasks.builder()
                        .id(evaluation.getId())
                        .loginName(user.getLoginName())
                        .name(user.getName())
                        .during(job.getDuring())
                        .status(relation.getStatus())
                        .userId(user.getId())
                        .jobName(job.getJobLevelName() + "-" + job.getJobPositionTarget())
                        .build();

                if (Math.abs(relation.getStatus()) <= 3) {
                    expertTasks.setTaskId(job.getApplyTaskID());
                    expertTasks.setType("岗位申请");
                } else {
                    expertTasks.setTaskId(job.getAppraisalTaskID());
                    expertTasks.setType("岗位考核");
                }

                data.add(expertTasks);
            }
        } else {
            List<User> users = userClient.getUserXML(teacherDTO.getLoginName(), teacherDTO.getName()).getData();
            for (User user : users) {
                UserJobRelations relation = jobClient.getUserJobByUserId(user.getId()).getData();
                if (!("岗位申请".equals(teacherDTO.getType()) && Math.abs(relation.getStatus()) > 3) &&
                        !("岗位考核".equals(teacherDTO.getType()) && Math.abs(relation.getStatus()) < 10)) {
                    throw new ServiceException("请重新确认数据");
                }
                Job job = jobClient.getJob(relation.getJobId()).getData();
                Evaluation evaluation = evaluationMapper.getMyEvaluation(relation.getId(), userId);
                if (evaluation == null) {
                    continue;
                }

                ExpertTasks expertTask = ExpertTasks.builder()
                        .id(user.getId())
                        .loginName(user.getLoginName())
                        .name(user.getName())
                        .during(job.getDuring())
                        .status(relation.getStatus())
                        .userId(relation.getUserId())
                        .build();
                if (Math.abs(relation.getStatus()) < 10) {
                    expertTask.setTaskId(job.getApplyTaskID());
                    expertTask.setType("岗位申请");
                } else {
                    expertTask.setTaskId(job.getAppraisalTaskID());
                    expertTask.setType("岗位考核");
                }

                data.add(expertTask);
            }
        }

        return Response.success(data);
    }

    @Override
    public ResponseVO<Map<String, Object>> deleteEvaluation(String token, DeleteEvaluationDTO deleteEvaluationDTO) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        UserJobRelations relation = jobClient.getUserJob(deleteEvaluationDTO.getUserId(), deleteEvaluationDTO.getJobId()).getData();
        if (relation == null) {
            throw new ServiceException("数据不存在");
        }

        evaluationMapper.deleteEvaluation(relation.getId(), deleteEvaluationDTO.getExpertId());

        return Response.success(null);
    }

    @Override
    public ResponseVO<Integer> adminAppraisalTask(String token, Evaluation evaluation) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        UserJobRelations relation = jobClient.getUserJobById(evaluation.getUserJobRelationsId()).getData();
        if (relation == null) {
            throw new ServiceException("数据不存在");
        }

        int resAbsStatus = Math.abs(evaluation.getStatus());
        int userJobAbsStatus = Math.abs(relation.getStatus());

//        List<Evaluation> allEvaluation;
        if (relation.getStatus() == 2 || userJobAbsStatus == 3) {
            if (resAbsStatus != 3) {
                throw new ServiceException("请重新确认数据");
            }
//            allEvaluation = evaluationMapper.getFirstDuringEvaluationXML(relation.getId());
            if (evaluationMapper.updateStatus(evaluation.getStatus(), relation.getId()) < 1) {
                throw new ServiceException("修改失败");
            }
            if (jobClient.updateUserJobStatus(relation.getId(), evaluation.getStatus()).getData() < 1) {
                throw new ServiceException("修改失败");
            }
        } else if (relation.getStatus() == 12 || userJobAbsStatus == 13) {
//            allEvaluation = evaluationMapper.getSecondDuringEvaluationXML(relation.getId());
            if (resAbsStatus != 13) {
                throw new ServiceException("请重新确认数据");
            }
            if (evaluationMapper.updateStatus(relation.getId(), evaluation.getStatus()) < 1) {
                throw new ServiceException("修改失败");
            }
            if (jobClient.updateUserJobStatus(relation.getId(), evaluation.getStatus()).getData() < 1) {
                throw new ServiceException("修改失败");
            }
        } else {
            if (util.IsAllExpertAgree(relation)) {
                if (userJobAbsStatus < 4) {
                    if (evaluationMapper.updateStatus(relation.getId(), 2) < 1) {
                        throw new ServiceException("修改失败");
                    }
                } else {
                    if (evaluationMapper.updateStatus(relation.getId(), 12) < 1) {
                        throw new ServiceException("修改失败");
                    }
                }
            }
        }

        return Response.success(evaluation.getId());
    }

    @Override
    public ResponseVO<List<Evaluation>> getEvaluationInfo(String token, UserJobRelations relations) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        List<Evaluation> data;

        if (userClient.isAdminOrLeadership(userId).getData()) {
            data = evaluationMapper.getEvaluationsByUserJobId(relations.getId());
        } else if (userClient.isTeacher(userId).getData()) {
            UserJobRelations relations_t = jobClient.getUserJobById(relations.getId()).getData();
            if (!userId.equals(relations_t.getUserId())) {
                throw new ServiceException("没有权限");
            }
            data = evaluationMapper.getEvaluationsByUserJobId(relations.getId());
        } else {
            throw new ServiceException("没有权限");
        }

        return Response.success(data);
    }

    @Override
    public ResponseVO<Integer> expertAppraisalTask(String token, Evaluation evaluation) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isTeacher(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        Evaluation preEvaluation = evaluationMapper.getEvaluationById(evaluation.getId());
        if (preEvaluation == null) {
            throw new ServiceException("数据不存在");
        }

        UserJobRelations relation = jobClient.getUserJobById(evaluation.getUserJobRelationsId()).getData();
        if (relation == null) {
            throw new ServiceException("数据不存在");
        }


        int absStatus = Math.abs(relation.getStatus());
        if (!(absStatus <= 2) && !(absStatus > 10 && absStatus <= 12)) {
            throw new ServiceException("没有权限");
        }

        if (!evaluation.getExpertId().equals(userId)) {
            throw new ServiceException("没有权限");
        }

        Integer status = evaluation.getStatus();
        int resAbsStatus = Math.abs(status);
        int evaAbsStatus = Math.abs(preEvaluation.getStatus());
        if (resAbsStatus != 2 && resAbsStatus != 12) {
            throw new ServiceException("请重新确认数据");
        } else {
            if (relation.getStatus() == 1 || absStatus == 2) {
                if (resAbsStatus != 2 || (resAbsStatus == 2 && evaAbsStatus != 1 && evaAbsStatus != 2)) {
                    throw new ServiceException("请重新确认数据");
                }
            } else if (relation.getStatus() == 11 || absStatus == 12) {
                if (resAbsStatus != 12 || (resAbsStatus == 12 && !(evaAbsStatus == 11 || evaAbsStatus == 12))) {
                    throw new ServiceException("请重新确认数据");
                }
            }
        }
        preEvaluation.setComment(evaluation.getComment());
        preEvaluation.setStatus(evaluation.getStatus());

        if (evaluationMapper.updateEvaluation(preEvaluation)) {
            throw new ServiceException("修改失败");
        }

        if (preEvaluation.getStatus() == -2 || preEvaluation.getStatus() == -12) {
            if (jobClient.updateUserJobStatus(preEvaluation.getUserJobRelationsId(), preEvaluation.getStatus()).getData() < 1) {
                throw new ServiceException("修改失败");
            }
        }

        if (absStatus <= -2) {
            if (util.IsAllExpertAgree(relation)) {
                if (jobClient.updateUserJobStatus(relation.getId(), 2).getData() < 1) {
                    throw new ServiceException("修改失败");
                }
            } else if (evaluation.getStatus() == -2) {
                if (jobClient.updateUserJobStatus(relation.getId(), -2).getData() < 1) {
                    throw new ServiceException("修改失败");
                }
            }
        } else {
            if (util.IsAllExpertAgree(relation)) {
                if (jobClient.updateUserJobStatus(relation.getId(), 12).getData() < 1) {
                    throw new ServiceException("修改失败");
                }
            } else if (evaluation.getStatus() == -12) {
                if (jobClient.updateUserJobStatus(relation.getId(), -12).getData() < 1) {
                    throw new ServiceException("修改失败");
                }
            }
        }

        return Response.success(preEvaluation.getId());
    }
}
