package com.qks.common.vo;

import com.qks.common.po.Task;
import com.qks.common.po.TaskFile;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-13 15:08
 */
@Data
@Builder
public class ExTask {
    private Task task;
    private List<TaskFile> files;
    private List<ExTask> subExTask;
}
