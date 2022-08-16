package com.qks.job.controller;

import com.qks.common.exception.ServiceException;
import com.qks.common.po.Job;
import com.qks.common.po.JobNameNode;
import com.qks.common.po.UserJobRelations;
import com.qks.common.vo.ResponseVO;
import com.qks.common.vo.SelfJobVO;
import com.qks.job.service.JobClientService;
import com.qks.job.service.JobService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-16 20:41
 */
@RestController
@RequestMapping("/api/job")
public class JobClientController {

    @Resource
    private JobClientService jobClientService;

    @PostMapping("/teachers/target/userId")
    ResponseVO<UserJobRelations> getUserJobByUserId(Integer userId) {
        return jobClientService.getUserJobByUserId(userId);
    }

}
