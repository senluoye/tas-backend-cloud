package com.qks.job.service.impl;

import com.qks.common.exception.ServiceException;
import com.qks.common.po.*;
import com.qks.common.utils.JwtUtils;
import com.qks.common.utils.Response;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.SelfJobVO;
import com.qks.common.vo.UserInfo;
import com.qks.job.mapper.JobClientMapper;
import com.qks.job.mapper.JobMapper;
import com.qks.job.service.JobClientService;
import com.qks.job.service.JobService;
import com.qks.job.utils.JobUtil;
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
public class JobClientServiceImpl implements JobClientService {

    @Resource
    private JobClientMapper jobClientMapper;

    @Override
    public ResponseVO<UserJobRelations> getUserJobByUserId(Integer userId) {
        UserJobRelations userJobRelations = jobClientMapper.getUserJobByUserId(userId);
        return Response.success(userJobRelations);
    }
}
