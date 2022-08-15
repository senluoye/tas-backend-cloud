package com.qks.task.controller;

import com.qks.common.dto.task.SubTaskDTO;
import com.qks.common.dto.task.TaskDTO;
import com.qks.common.exception.ServiceException;
import com.qks.common.po.Task;
import com.qks.common.po.TaskFile;
import com.qks.common.vo.ExTask;
import com.qks.common.vo.ResponseVO;
import com.qks.task.service.TaskService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
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
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("get-task-tree")
    public ResponseVO<ExTask> getTaskTree(@RequestHeader("token") String token,
                                          @RequestParam("taskID") String taskId,
                                          @RequestParam("userId") String userId) throws ServiceException {
        return taskService.getTaskTree(token, taskId, userId);
    }

    @GetMapping("create-sub-task")
    public ResponseVO<Task> createSubTask(@RequestHeader("token") String token,
                                          @RequestBody SubTaskDTO subTaskDTO) throws ServiceException {
        return taskService.createSubTask(token, subTaskDTO);
    }

    @GetMapping("delete-task-tree")
    public ResponseVO<ExTask> deleteTaskTree(@RequestHeader("token") String token,
                                       @RequestBody Integer taskId) throws ServiceException {
        return taskService.deleteTaskTree(token, taskId);
    }

    @GetMapping("modify-task")
    public ResponseVO<Map<String, Object>> modifyTask(@RequestHeader("token") String token,
                                   @RequestBody TaskDTO taskDTO) throws ServiceException {
        return taskService.modifyTask(token, taskDTO);
    }

    @GetMapping("upload-file")
    public ResponseVO<TaskFile> uploadFile(@RequestHeader("token") String token,
                                           @RequestParam("taskId") String taskId,
                                           @RequestParam("jobId") String jobId,
                                           @RequestParam("file") MultipartFile file) throws ServiceException {
        return taskService.uploadFile(token, Integer.parseInt(taskId), Integer.parseInt(jobId), file);
    }

    @GetMapping("get-file")
    public byte[] getFile(@RequestHeader("token") String token,
                                @RequestParam("fileName") String fileName) throws ServiceException {
        return taskService.getFile(token, fileName);
    }

    @GetMapping("delete-file")
    public ResponseVO<Map<String, Object>> deleteFile(@RequestHeader("token") String token,
                                   @RequestBody String taskFileId) throws ServiceException {
        return taskService.deleteFile(token, Integer.parseInt(taskFileId));
    }

}
