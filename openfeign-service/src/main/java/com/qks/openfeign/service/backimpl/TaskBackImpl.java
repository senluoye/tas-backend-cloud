package com.qks.openfeign.service.backimpl;

import com.qks.common.po.Job;
import com.qks.common.po.Task;
import com.qks.common.po.UserJobRelations;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.UserJobRelationVO;
import com.qks.openfeign.service.JobClient;
import com.qks.openfeign.service.TaskClient;
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
public class TaskBackImpl implements FallbackFactory<TaskClient> {

    @Override
    public TaskClient create(Throwable throwable) {
        return new TaskClient() {
            @Override
            public ResponseVO<Task> addTask(Task applyTask) {
                return null;
            }

            @Override
            public ResponseVO<Task> getTask(Integer oldTaskId) {
                return null;
            }

            @Override
            public ResponseVO<Boolean> updateTask(Task newTask) {
                return null;
            }
        };
    }
}
