package com.qks.job.mapper;

import com.qks.common.po.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:48
 */
@Mapper
public interface JobMapper {

    boolean addTask(Task applyTask);
}
