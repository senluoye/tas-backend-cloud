package com.qks.task.service;

import com.qks.common.dto.task.SubTaskDTO;
import com.qks.common.dto.task.TaskDTO;
import com.qks.common.exception.ServiceException;
import com.qks.common.po.Task;
import com.qks.common.po.TaskFile;
import com.qks.common.vo.ExTask;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.SelfJobVO;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:39
 */
public interface TaskService {

    ResponseVO<ExTask> getTaskTree(String token, String taskId, String userId) throws ServiceException;

    ResponseVO<Task> createSubTask(String token, SubTaskDTO subTaskDTO) throws ServiceException;

    ResponseVO<ExTask> deleteTaskTree(String token, Integer taskId) throws ServiceException;

    ResponseVO<Map<String, Object>> modifyTask(String token, TaskDTO taskDTO) throws ServiceException;

    ResponseVO<TaskFile> uploadFile(String token, Integer taskId, Integer jobId, MultipartFile file) throws ServiceException;

    byte[] getFile(String token, String fileName) throws ServiceException;

    ResponseVO<Map<String, Object>> deleteFile(String token, int parseInt) throws ServiceException;
}
