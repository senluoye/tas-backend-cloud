package com.qks.evaluation.utils;

import com.qks.common.po.Evaluation;
import com.qks.common.po.UserJobRelations;
import com.qks.common.vo.UserJobRelationVO;
import com.qks.evaluation.mapper.EvaluationMapper;
import com.qks.openfeign.service.JobClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-09 18:21
 */
@Component
public class EvaluationUtil {

    @Resource
    private JobClient jobClient;

    @Resource
    private EvaluationMapper evaluationMapper;

    public Boolean IsAllExpertAgree(UserJobRelations relations) {
        int absStatus = Math.abs(relations.getStatus());
        List<Evaluation> evaluations = evaluationMapper.getTeacherEvaluationsByUserJobId(relations.getId());
        if (absStatus >= 1 && absStatus <= 2) {
            for (Evaluation evaluation : evaluations) {
                if (evaluation.getStatus() >= -2 && evaluation.getStatus() <= 2) {
                    return false;
                }
            }
            return true;
        } else if (absStatus >= 11 && absStatus <= 12) {
            for (Evaluation evaluation : evaluations) {
                if (Math.abs(evaluation.getStatus()) == 11 || evaluation.getStatus() == -12) {
                    return false;
                }
            }
            return true;
        } else if (absStatus == 3 || absStatus == 13) {
            return true;
        }

        return false;
    }

    public static Integer[] getApplyLis(Integer status, Integer types) {
        if (status == 2) {
            if (types == 1) {
                return new Integer[]{1};
            } else if (types == 2) {
                return new Integer[]{2};
            } else if (types == 0) {
                return new Integer[]{1, 2};
            }
        } else if (status == 3) {
            if (types == 1) {
                return new Integer[]{2};
            } else if (types == 2) {
                return new Integer[]{12};
            } else if (types == 0){
                return new Integer[]{2, 12};
            }
        } else if (status == 4) {
            if (types == 1) {
                return new Integer[]{3};
            } else if (types == 2) {
                return new Integer[]{13};
            } else if (types == 0) {
                return new Integer[]{3, 13};
            }
        } else if (status == 0) {
            if (types == 1) {
                return new Integer[]{1, 2, 3, -2, -3};
            } else if (types == 2) {
                return new Integer[]{11, 12, 13, -12, -13};
            } else if (types == 0) {
                return new Integer[]{1, 2, 3, -2, -3, 11, 12, 13, -12, -13};
            }
        }

        return new Integer[]{};
    }
}
