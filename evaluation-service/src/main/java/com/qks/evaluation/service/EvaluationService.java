package com.qks.evaluation.service;

import com.qks.common.dto.evaluation.ApplyDTO;
import com.qks.common.dto.evaluation.DeleteEvaluationDTO;
import com.qks.common.dto.evaluation.EvaluateDTO;
import com.qks.common.dto.evaluation.TeacherDTO;
import com.qks.common.exception.ServiceException;
import com.qks.common.po.Evaluation;
import com.qks.common.po.User;
import com.qks.common.po.UserJobRelations;
import com.qks.common.po.UserRoleRelations;
import com.qks.common.vo.AdminTasks;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.TeacherInfo;
import com.qks.common.vo.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:39
 */
public interface EvaluationService {

    ResponseVO<List<User>> getEvaluationList(String token, User user);

    ResponseVO<List<TeacherInfo>> getAppliedTeacher(String token, TeacherDTO teacherDTO);

    ResponseVO<Map<String, Object>> assignApplyExpertEvaluation(String token, EvaluateDTO evaluateDTO);

    ResponseVO<List<AdminTasks>> adminGetTeacherList(String token, TeacherDTO teacherDTO);

    ResponseVO<Map<String, Object>> getTeacherList(String token, TeacherDTO teacherDTO);

    ResponseVO<Map<String, Object>> deleteEvaluation(String token, DeleteEvaluationDTO deleteEvaluationDTO);

    ResponseVO<Integer> adminAppraisalTask(String token, Evaluation evaluation);

    ResponseVO<Map<String, Object>> getEvaluationInfo(String token, UserJobRelations relations);

    ResponseVO<List<TeacherInfo>> expertAppraisalTask(String token, User user);
}
