package com.qks.openfeign.service.backimpl;

import com.qks.common.po.Evaluation;
import com.qks.common.vo.ResponseVO;
import com.qks.openfeign.service.EvaluationClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName 降级类
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-06 00:09
 */
@Slf4j
@Component
public class EvaluationBackImpl implements FallbackFactory<EvaluationClient> {

    @Override
    public EvaluationClient create(Throwable throwable) {
        return new EvaluationClient() {

            @Override
            public Integer deleteEvaluation(Integer userId) {
                return null;
            }

            @Override
            public List<Evaluation> getDuringEvaluationXML(Integer status) {
                return null;
            }

            @Override
            public ResponseVO<Boolean> updateApplyUserJob(Integer id, int status) {
                return null;
            }

            @Override
            public ResponseVO<Boolean> updateEvaluationUserJob(Integer id, int i) {
                return null;
            }

            @Override
            public ResponseVO<Boolean> updateUserJob(Integer id) {
                return null;
            }
        };
    }
}
