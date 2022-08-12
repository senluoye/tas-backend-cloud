package com.qks.job.utils;

import cn.hutool.cron.TaskLauncher;
import com.qks.common.exception.ServiceException;
import com.qks.common.po.*;
import com.qks.job.mapper.JobMapper;
import com.qks.openfeign.service.EvaluationClient;
import com.qks.openfeign.service.JobClient;
import com.qks.openfeign.service.TaskClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-09 18:21
 */
@Component
public class JobUtil {
    @Resource
    private JobMapper jobMapper;

    @Resource
    private EvaluationClient evaluationClient;

    @Resource
    private TaskClient taskClient;

    public void createJobNameNode(Job job) throws ServiceException {
        String A = job.getJobType();
        String B = job.getJobLevelName();
        Integer C = job.getRank();
        String D = job.getJobPositionTarget();
        String E = job.getJobDoctorTarget();

        JobNameNode ANameNode = CreateJobNameNodeOrAddCnt(A, -1);
        JobNameNode BNameNode = CreateJobNameNodeOrAddCnt(B, ANameNode.getId());
        JobNameNode CNameNode = CreateJobNameNodeOrAddCnt(C.toString(), BNameNode.getId());

        if ("".equals(D)) return;
        JobNameNode DNameNode = CreateJobNameNodeOrAddCnt(D, CNameNode.getId());

        if ("".equals(E)) return;
        JobNameNode ENameNode = CreateJobNameNodeOrAddCnt(E, DNameNode.getId());
    }

    public JobNameNode CreateJobNameNodeOrAddCnt(String name, Integer parentId) throws ServiceException {
        if (jobMapper.getJobNameNode(name, parentId) == null) {
            JobNameNode jobNameNode = JobNameNode.builder()
                    .name(name)
                    .parentId(parentId)
                    .cnt(1)
                    .build();
            if (!jobMapper.createJobNameNode(jobNameNode)) {
                throw new ServiceException("创建职位名称节点失败");
            }
            return jobNameNode;
        }
        return null;
    }

    public void PruneJobNameNode(Job job) throws ServiceException {
        String A = job.getJobType();
        String B = job.getJobLevelName();
        Integer C = job.getRank();
        String D = job.getJobPositionTarget();
        String E = job.getJobDoctorTarget();

        JobNameNode ANameNode = tryToDeleteJobNameNode(A, -1);
        JobNameNode BNameNode = tryToDeleteJobNameNode(B, ANameNode.getId());
        JobNameNode CNameNode = tryToDeleteJobNameNode(C.toString(), BNameNode.getId());

        if ("".equals(D)) return;
        JobNameNode DNameNode = tryToDeleteJobNameNode(D, CNameNode.getId());

        if ("".equals(E)) return;
        JobNameNode ENameNode = tryToDeleteJobNameNode(E, DNameNode.getId());
    }

    private JobNameNode tryToDeleteJobNameNode(String name, int parentId) throws ServiceException {
        JobNameNode node = jobMapper.getJobNameNode(name, parentId);
        node.setCnt(node.getCnt() - 1);
        if (node.getCnt() == 0) {
            if (!jobMapper.deleteJobNameNode(node.getId())) {
                throw new ServiceException("删除失败");
            }
        } else {
            if (!jobMapper.updateJobNameNodeCnt(node)) {
                throw new ServiceException("更新失败");
            }
        }
        return node;
    }

    public List<Job> filterJobByDuring(List<Job> jobs, String start, String end) {
        List<Job> result = new ArrayList<>();
        for (Job job : jobs) {
            String[] jobDuringSplit = job.getDuring().split("-");
            String left = jobDuringSplit[0];
            String right = jobDuringSplit[1];

            boolean x = isTimeAThanOrEqualB(left, start);
            boolean y = isTimeAThanOrEqualB(end, right);
            if (x && y) {
                result.add(job);
            }
        }

        return result;
    }

    public boolean isTimeAThanOrEqualB(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return a.charAt(i) > b.charAt(i);
            }
        }

        return true;
    }

    public void ChangeEvaluationStatus(UserJobRelations relations) throws ServiceException {
        Integer status = relations.getStatus();
        if (status == -2 || status == -3) {
            if (!evaluationClient.updateApplyUserJob(relations.getId(), 1).getData()) {
                throw new ServiceException("更新status错误");
            }
        } else if (status == -12 || status == -13) {
            if (!evaluationClient.updateEvaluationUserJob(relations.getId(), 11).getData()) {
                throw new ServiceException("更新status错误");
            }
        }
    }

    public void DeleteOldEvaluation(UserJobRelations relation) throws ServiceException {
        if (relation.getStatus() == 3) {
            if (!evaluationClient.updateUserJob(relation.getId()).getData()) {
                throw new ServiceException("更新失败");
            }
        }
    }

    public void copyTaskTree(Integer newTaskId, Integer oldTaskId) {
        Task oldTask = taskClient.getTask(oldTaskId).getData();
        Task newTask = taskClient.getTask(newTaskId).getData();

        for (Task task : oldTask.getSubTasks()) {
            task.setId(0);
            List<Task> subTasks = newTask.getSubTasks();
            newTask.setSubTasks(subTasks);
        }

        taskClient.updateTask(newTask);

        for (int i = 0; i < newTask.getSubTasks().size(); i++) {
            copyTaskTree(newTask.getSubTasks().get(i).getId(), oldTask.getSubTasks().get(i).getId());
        }
    }
}
