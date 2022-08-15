package com.qks.task.service.impl;

import com.qks.common.dto.task.SubTaskDTO;
import com.qks.common.dto.task.TaskDTO;
import com.qks.common.exception.ServiceException;
import com.qks.common.po.Job;
import com.qks.common.po.Task;
import com.qks.common.po.TaskFile;
import com.qks.common.po.User;
import com.qks.common.utils.JwtUtils;
import com.qks.common.utils.Response;
import com.qks.common.vo.ExTask;
import com.qks.common.vo.ResponseVO;
import com.qks.openfeign.service.JobClient;
import com.qks.openfeign.service.UserClient;
import com.qks.task.mapper.TaskMapper;
import com.qks.task.service.TaskService;
import com.qks.task.utils.MinioUtils;
import com.qks.task.utils.TaskUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
public class TaskServiceImpl implements TaskService {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private UserClient userClient;

    @Resource
    private JobClient jobClient;

    @Resource
    private TaskUtil util;

    @Resource
    private MinioUtils minioUtils;

    @Override
    public ResponseVO<ExTask> getTaskTree(String token, String taskIdStr, String targetUserIdStr) throws ServiceException {
        if ("".equals(taskIdStr)) {
            throw new ServiceException("任务id不能为空");
        }
        Integer taskId = Integer.parseInt(taskIdStr);
        Integer targetUserId = Integer.parseInt(targetUserIdStr);
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());

        if (!"".equals(targetUserIdStr) && !targetUserId.equals(userId) &&!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        if ("".equals(targetUserIdStr)) {
            userId = targetUserId;
        }

        Task task = taskMapper.getTask(taskId);
        if (task == null) {
            throw new ServiceException("任务不存在");
        }

        task.setSubTasks(taskMapper.getSubTasks(taskId));
        ExTask exTask = ExTask.builder()
                .task(task)
                .build();
        exTask = util.LoadTaskTree(exTask, userId);

        return Response.success(exTask);
    }

    @Override
    public ResponseVO<Task> createSubTask(String token, SubTaskDTO subTaskDTO) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        if (subTaskDTO.getParentTaskId() == 0 || "".equals(subTaskDTO.getName()) || "".equals(subTaskDTO.getDescription())) {
            throw new ServiceException("参数错误");
        }

        Task task = taskMapper.getTask(subTaskDTO.getParentTaskId());
        if (task == null) {
            throw new ServiceException("任务不存在");
        }
        task.setSubTasks(taskMapper.getSubTasks(task.getId()));

        Task subTask = Task.builder()
                .name(subTaskDTO.getName())
                .description(subTaskDTO.getDescription())
                .build();
        task.getSubTasks().add(subTask);
        if (taskMapper.addTask(subTask) == 0) {
            throw new ServiceException("创建子任务失败");
        }
        if (taskMapper.addSubTask(task.getId(), subTask.getId()) == 0) {
            throw new ServiceException("创建子任务失败");
        }

        return Response.success(task);
    }

    @Override
    public ResponseVO<ExTask> deleteTaskTree(String token, Integer taskId) throws ServiceException {
        if (taskId == 0) {
            throw new ServiceException("参数错误");
        }

        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        Task task = taskMapper.getTask(taskId);
        task.setSubTasks(taskMapper.getSubTasks(taskId));

        ExTask exTask = util.LoadTaskTree(ExTask.builder().task(task).build(), userId);
        util.DeleteTaskTree(task);

        return Response.success(exTask);
    }

    @Override
    public ResponseVO<Map<String, Object>> modifyTask(String token, TaskDTO taskDTO) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        if (!userClient.isAdminOrLeadership(userId).getData()) {
            throw new ServiceException("没有权限");
        }

        if (taskDTO.getTaskId() == 0) {
            throw new ServiceException("参数错误");
        }

        Task task = taskMapper.getTask(taskDTO.getTaskId());
        if (task == null) {
            throw new ServiceException("任务不存在");
        }

        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());

        if (!taskMapper.updateTask(task)) {
            throw new ServiceException("更新失败");
        }

        return Response.success(null);
    }

    @Override
    public ResponseVO<TaskFile> uploadFile(String token, Integer taskId, Integer jobId, MultipartFile file) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());
        String fileName = file.getOriginalFilename();

        User user = userClient.getUser(userId).getData();
        Job job = jobClient.getJob(jobId).getData();
        Task task = taskMapper.getTask(taskId);
        if (user == null || job == null || task == null) {
            throw new ServiceException("参数错误");
        }

        TaskFile t = taskMapper.getTaskFileByStatus(taskId, userId, fileName);
        if (t != null) {
            throw new ServiceException("文件已经存在");
        }

        TaskFile taskFile = TaskFile.builder()
                .userId(userId)
                .taskId(taskId)
                .name(fileName)
                .minioPath(job.getJobType() + "/" + job.getJobLevelName() + job.getRank() + "/" +
                        job.getJobPositionTarget() + "/" + job.getJobDoctorTarget() + "/" +
                        user.getLoginName() + "-" + user.getName() + "/" + fileName)
                .build();
        try {
            minioUtils.upload(taskFile.getMinioPath(), file);
        } catch (Exception e) {
            throw new ServiceException("上传失败");
        }

        if (taskMapper.addTaskFile(taskFile) == 0) {
            throw new ServiceException("上传失败");
        }

        return null;
    }

    @Override
    public byte[] getFile(String token, String fileName) throws ServiceException {
        if (!JwtUtils.verify(token)) {
            throw new ServiceException("token错误");
        }

        try {
            return minioUtils.getFile(fileName);
        } catch (Exception e) {
            throw new ServiceException("获取失败");
        }
    }

    @Override
    public ResponseVO<Map<String, Object>> deleteFile(String token, int taskFileId) throws ServiceException {
        Integer userId = Integer.valueOf(JwtUtils.parser(token).get("userId").toString());

        TaskFile taskFile = taskMapper.getTaskFile(taskFileId);
        if (taskFile == null || !Objects.equals(taskFile.getUserId(), userId)) {
            throw new ServiceException("文件不存在");
        }

        if (!taskMapper.deleteTaskFile(taskFileId)) {
            throw new ServiceException("删除失败");
        }

        return null;
    }
}
