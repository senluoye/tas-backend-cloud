package com.qks.openfeign.service.backimpl;

import com.qks.common.exception.ServiceException;
import com.qks.common.po.Job;
import com.qks.common.po.User;
import com.qks.common.po.UserJobRelations;
import com.qks.common.utils.Response;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.UserJobRelationVO;
import com.qks.openfeign.service.JobClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-06 00:09
 */
@Slf4j
@Component
public class JobBackImpl implements FallbackFactory<JobClient> {

    @Override
    public JobClient create(Throwable throwable) {
        return new JobClient() {

            @Override
            public ResponseVO<List<String>> getJobs() {
                return null;
            }

            @Override
            public ResponseVO<List<String>> getAllJobDoctorTarget() {
                return null;
            }

            @Override
            public List<UserJobRelationVO> getUserJobXML(String type) {
                return null;
            }

            @Override
            public ResponseVO<UserJobRelations> getUserJob(Integer userId, Integer jobId) {
                return null;
            }

            @Override
            public ResponseVO<Integer> deleteUserJob(Integer id) {
                return null;
            }

            @Override
            public ResponseVO<List<UserJobRelations>> getUserJobs(Integer id, Integer[] allys) {
                return null;
            }

            @Override
            public ResponseVO<Job> getJob(Integer jobId) {
                return null;
            }

            @Override
            public ResponseVO<UserJobRelations> getUserJobByUserId(Integer userId) throws ServiceException {
                throw new ServiceException("操作速度过快");
            }

            @Override
            public ResponseVO<UserJobRelations> getUserJobById(Integer id) {
                return null;
            }

            @Override
            public ResponseVO<Integer> updateUserJobStatus(Integer id, Integer status) {
                return null;
            }
        };
    }
}
