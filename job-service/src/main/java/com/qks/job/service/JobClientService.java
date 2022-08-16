package com.qks.job.service;

import com.qks.common.exception.ServiceException;
import com.qks.common.po.Job;
import com.qks.common.po.JobNameNode;
import com.qks.common.po.UserJobRelations;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.SelfJobVO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:39
 */
public interface JobClientService {

    ResponseVO<UserJobRelations> getUserJobByUserId(Integer userId);
}
