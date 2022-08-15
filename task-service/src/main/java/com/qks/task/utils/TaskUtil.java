package com.qks.task.utils;

import com.qks.common.exception.ServiceException;
import com.qks.common.po.Task;
import com.qks.common.po.TaskFile;
import com.qks.common.vo.ExTask;
import com.qks.task.mapper.TaskMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-09 18:21
 */
@Component
public class TaskUtil {

    @Resource
    private TaskMapper taskMapper;

    public ExTask LoadTaskTree(ExTask root, Integer userId) {
        Queue<ExTask> queue = new LinkedList<>();
        queue.add(root);

        while (queue.size() > 0) {
            ExTask exTask = queue.poll();
            List<TaskFile> taskFiles = taskMapper.getTaskFilesByIdAndUser(exTask.getTask().getId(), userId);
            exTask.setFiles(taskFiles);

            for (Task subTask : exTask.getTask().getSubTasks()) {
                subTask.setSubTasks(taskMapper.getSubTasks(subTask.getId()));
                ExTask nextExTask = ExTask.builder().task(subTask).build();
                exTask.getSubExTask().add(nextExTask);
                queue.add(nextExTask);
            }
        }

        return root;
    }

    public void DeleteTaskTree(Task root) throws ServiceException {
        Queue<Task> queue = new LinkedList<>();
        Stack<Task> stack = new Stack<>();
        queue.add(root);
        stack.push(root);
        while (queue.size() > 0) {
            Task task = queue.poll();
            for (Task subTask : task.getSubTasks()) {
                queue.add(subTask);
                stack.push(subTask);
            }
        }

        while (stack.size() > 0) {
            Task task = stack.pop();
            if (!taskMapper.deleteTask(task.getId())) {
                throw new ServiceException("删除子节点失败");
            }
        }
    }
}
