package com.qks.task.mapper;

import com.qks.common.po.TaskFile;
import com.qks.common.po.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:48
 */
@Mapper
public interface TaskMapper {

    @Select("select * from tasks where id = #{id}")
    Task getTask(Integer id);

    @Select("select * from tasks where id in (" +
            "   select subtask_id from task_subtask where task_id = #{taskId}" +
            ")")
    List<Task> getSubTasks(Integer taskId);

    @Select("select * from task_file where task_id = #{taskId} and user_id = #{userId}")
    List<TaskFile> getTaskFilesByIdAndUser(Integer id, Integer userId);

    @Insert("insert into task_subtask (task_id, subtask_id) values (#{taskId}, #{subTaskId})")
    int addSubTask(Integer taskId, Integer subTaskId);

    @Insert("insert into tasks(name, description) values (#{name}, #{description})")
    int addTask(Task subTask);

    @Delete("delete from tasks where id = #{id}")
    boolean deleteTask(Integer id);

    @Update("update tasks set name = #{name}, description = #{description} where id = #{id}")
    boolean updateTask(Task task);

    @Select("select * " +
            "from task_file " +
            "where task_id = #{taskId} and user_id = #{userId} and name = #{fileName}")
    TaskFile getTaskFileByStatus(Integer taskId, Integer userId, String fileName);

    @Insert("insert into task_file (name, minio_path, id, user_id, task_id) " +
            "values (#{name}, #{minioPath}, #{id}, #{userId}, #{taskId})")
    int addTaskFile(TaskFile taskFile);

    @Select("select * from task_file where id = #{id}")
    TaskFile getTaskFile(int taskFileId);

    @Delete("delete from task_file where id = #{id}")
    boolean deleteTaskFile(int taskFileId);
}
