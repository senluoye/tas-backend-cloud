package com.qks.evaluation.service;

import com.qks.common.dto.evaluation.*;
import com.qks.common.exception.ServiceException;
import com.qks.common.po.Evaluation;
import com.qks.common.po.User;
import com.qks.common.po.UserJobRelations;
import com.qks.common.po.UserRoleRelations;
import com.qks.common.vo.*;

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

    ResponseVO<List<User>> getEvaluationList(String token, User user) throws ServiceException;

    ResponseVO<List<TeacherInfo>> getAppliedTeacher(String token, TeacherDTO teacherDTO);

    ResponseVO<Map<String, Object>> assignApplyExpertEvaluation(String token, EvaluateDTO evaluateDTO) throws ServiceException;

    ResponseVO<List<AdminTasks>> adminGetTeacherList(String token, TeacherDTO teacherDTO) throws ServiceException;

    ResponseVO<List<ExpertTasks>> getTeacherList(String token, TeacherListDTO teacherDTO) throws ServiceException;

    ResponseVO<Map<String, Object>> deleteEvaluation(String token, DeleteEvaluationDTO deleteEvaluationDTO) throws ServiceException;

    ResponseVO<Integer> adminAppraisalTask(String token, Evaluation evaluation) throws ServiceException;

    ResponseVO<List<Evaluation>> getEvaluationInfo(String token, UserJobRelations relations) throws ServiceException;

    ResponseVO<Integer> expertAppraisalTask(String token, Evaluation evaluation) throws ServiceException;
}
