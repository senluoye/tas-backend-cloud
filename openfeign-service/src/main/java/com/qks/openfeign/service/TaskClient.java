package com.qks.openfeign.service;

import com.qks.common.po.Task;
import com.qks.common.po.User;
import com.qks.common.vo.ResponseVO;
import com.qks.openfeign.service.backimpl.JobBackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-06 00:04
 */
@FeignClient(value = "taskservice", fallbackFactory = JobBackImpl.class)
public interface TaskClient {

    @PostMapping("/api/task")
    ResponseVO<Task> addTask(Task applyTask);

    @PostMapping("/api/task/id")
    ResponseVO<Task> getTask(Integer oldTaskId);

    @PutMapping("/api/task")
    ResponseVO<Boolean> updateTask(Task newTask);
}
