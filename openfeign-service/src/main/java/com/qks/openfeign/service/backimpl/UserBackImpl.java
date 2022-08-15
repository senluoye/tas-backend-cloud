package com.qks.openfeign.service.backimpl;

import com.qks.common.po.Job;
import com.qks.common.po.User;
import com.qks.common.po.UserJobRelations;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.UserJobRelationVO;
import com.qks.openfeign.service.JobClient;
import com.qks.openfeign.service.UserClient;
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
public class UserBackImpl implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {

            @Override
            public ResponseVO<Boolean> isAdminOrLeadership(Integer userId) {
                return null;
            }

            @Override
            public ResponseVO<List<User>> getExpertListXML(String name, String department) {
                return null;
            }

            @Override
            public ResponseVO<User> getUser(Integer userId) {
                return null;
            }

            @Override
            public ResponseVO<Boolean> isTeacher(Integer userId) {
                return null;
            }

            @Override
            public ResponseVO<Boolean> isExpert(Integer integer) {
                return null;
            }

            @Override
            public ResponseVO<List<User>> getUserXML(String loginName, String name) {
                return null;
            }
        };
    }
}
