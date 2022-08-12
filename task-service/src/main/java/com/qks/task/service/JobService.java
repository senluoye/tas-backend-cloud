package com.qks.task.service;

import com.qks.common.exception.ServiceException;
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
public interface JobService {

    ResponseVO<Job> createJob(String token, Job job) throws ServiceException;

    ResponseVO<Integer> deleteJob(String token, Integer id) throws ServiceException;

    ResponseVO<List<Job>> getJobList(String token, Map<String, String> queryMap);

    ResponseVO<Map<String, Object>> modifyJobInfo(String token, Job job) throws ServiceException;

    ResponseVO<Map<String, Object>> applyJob(String token, UserJobRelations relations) throws ServiceException;

    ResponseVO<List<JobNameNode>> selectJobNameNode(Integer id);

    ResponseVO<List<SelfJobVO>> getSelfJob(String token, Integer status) throws ServiceException;

    ResponseVO<Map<String, Object>> updateProgress(String token, UserJobRelations relations) throws ServiceException;

    ResponseVO<Job> copyJob(String token, Integer id) throws ServiceException;
}
