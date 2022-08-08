package com.qks.evaluation.service.impl;

import com.qks.common.dto.evaluation.DeleteEvaluationDTO;
import com.qks.common.dto.evaluation.EvaluateDTO;
import com.qks.common.dto.evaluation.TeacherDTO;
import com.qks.common.po.Evaluation;
import com.qks.common.po.User;
import com.qks.common.po.UserJobRelations;
import com.qks.common.utils.JwtUtils;
import com.qks.common.vo.AdminTasks;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.TeacherInfo;
import com.qks.evaluation.mapper.EvaluationMapper;
import com.qks.evaluation.service.EvaluationService;
import com.qks.openfeign.service.UserClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public ResponseVO<List<User>> getEvaluationList(String token, User user) {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            return ResponseVO.error("没有权限");
        }

        String name = user.getName();
        String department = user.getDepartment();

        return null;
    }

    @Override
    public ResponseVO<List<TeacherInfo>> getAppliedTeacher(String token, TeacherDTO teacherDTO) {
        return null;
    }

    @Override
    public ResponseVO<Map<String, Object>> assignApplyExpertEvaluation(String token, EvaluateDTO evaluateDTO) {
        return null;
    }

    @Override
    public ResponseVO<List<AdminTasks>> adminGetTeacherList(String token, TeacherDTO teacherDTO) {
        return null;
    }

    @Override
    public ResponseVO<Map<String, Object>> getTeacherList(String token, TeacherDTO teacherDTO) {
        return null;
    }

    @Override
    public ResponseVO<Map<String, Object>> deleteEvaluation(String token, DeleteEvaluationDTO deleteEvaluationDTO) {
        return null;
    }

    @Override
    public ResponseVO<Integer> adminAppraisalTask(String token, Evaluation evaluation) {
        return null;
    }

    @Override
    public ResponseVO<Map<String, Object>> getEvaluationInfo(String token, UserJobRelations relations) {
        return null;
    }

    @Override
    public ResponseVO<List<TeacherInfo>> expertAppraisalTask(String token, User user) {
        return null;
    }
}
