package com.qks.evaluation.controller;

import com.qks.common.dto.evaluation.DeleteEvaluationDTO;
import com.qks.common.dto.evaluation.EvaluateDTO;
import com.qks.common.dto.evaluation.TeacherDTO;
import com.qks.common.dto.evaluation.TeacherListDTO;
import com.qks.common.exception.ServiceException;
import com.qks.common.po.Evaluation;
import com.qks.common.po.User;
import com.qks.common.po.UserJobRelations;
import com.qks.common.vo.AdminTasks;
import com.qks.common.vo.ExpertTasks;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.TeacherInfo;
import com.qks.evaluation.service.EvaluationService;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/evaluation")
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    /**
     * 获取专家列表
     * @param token
     * @param user
     * @return
     * @throws ServiceException
     */
    @PostMapping("/experts-list")
    public ResponseVO<List<User>> getEvaluationList(@RequestHeader("token") String token,
                                                    @RequestBody User user) throws ServiceException {
        return evaluationService.getEvaluationList(token, user);
    }

    /**
     * 专家评审
     * @param token
     * @param user
     * @return
     * @throws ServiceException
     */
    @PostMapping("/experts")
    public ResponseVO<Integer> expertAppraisalTask(@RequestHeader("token") String token,
                                                           @RequestBody Evaluation evaluation) throws ServiceException {
        return evaluationService.expertAppraisalTask(token, evaluation);
    }

    /**
     * 分配专家审核信息
     * @param token
     * @param evaluateDTO
     * @return
     * @throws ServiceException
     */
    @PostMapping("/task")
    public ResponseVO<Map<String, Object>> assignApplyExpertEvaluation(@RequestHeader("token") String token,
                                @RequestBody EvaluateDTO evaluateDTO) throws ServiceException {
        return evaluationService.assignApplyExpertEvaluation(token, evaluateDTO);
    }

    /**
     * 专家获取需要审核的教师列表
     * @param token
     * @param teacherDTO
     * @return
     */
    @PostMapping("/teacher-list")
    public ResponseVO<List<ExpertTasks>> getTeacherList(@RequestHeader("token") String token,
                                                        @RequestBody TeacherListDTO teacherDTO) throws ServiceException {
        return evaluationService.getTeacherList(token, teacherDTO);
    }

    /**
     * 学院确认教师材料
     * @param token
     * @param teacherDTO
     * @return
     */
    @PostMapping("/teacher-list/admin")
    public ResponseVO<List<AdminTasks>> adminGetTeacherList(@RequestHeader("token") String token,
                                                            @RequestBody TeacherDTO teacherDTO) throws ServiceException {
        return evaluationService.adminGetTeacherList(token, teacherDTO);
    }

    /**
     * 删除某一专家的某一评审任务
     * @param token
     * @param deleteEvaluationDTO
     * @return
     */
    @PostMapping("/task")
    public ResponseVO<Map<String, Object>> deleteEvaluation(@RequestHeader("token") String token,
                                         @RequestBody DeleteEvaluationDTO deleteEvaluationDTO) throws ServiceException {
        return evaluationService.deleteEvaluation(token, deleteEvaluationDTO);
    }

    /**
     * 学院获取申请岗位的教师列表
     * @param token
     * @param teacherDTO
     * @return
     */
    @PostMapping("/teachers")
    public ResponseVO<List<TeacherInfo>> getAppliedTeacher(@RequestHeader("token") String token,
                                                        @RequestBody TeacherDTO teacherDTO) {
        return evaluationService.getAppliedTeacher(token, teacherDTO);
    }

    /**
     * 学院（管理员）评审
     * @param token
     * @param evaluation
     * @return
     */
    @PostMapping("/admin")
    public ResponseVO<Integer> adminAppraisalTask(@RequestHeader("token") String token,
                                          @RequestBody Evaluation evaluation) throws ServiceException {
        return evaluationService.adminAppraisalTask(token, evaluation);
    }

    /**
     * 查看专家评审记录
     * @param token
     * @param relations
     * @return
     */
    @PostMapping("/info")
    public ResponseVO<List<Evaluation>> getEvaluationInfo(@RequestHeader("token") String token,
                                           @RequestBody UserJobRelations relations) throws ServiceException {
        return evaluationService.getEvaluationInfo(token, relations);
    }
}
