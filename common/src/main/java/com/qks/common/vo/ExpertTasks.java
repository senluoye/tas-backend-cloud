package com.qks.common.vo;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-08 23:46
 */
@Data
@Builder
public class ExpertTasks {

    private Integer id;
    private String loginName;
    private String name;
    private String type;
    private String during;
    private Integer status;
    private Integer taskId;
    private Integer userId;
    private String jobName;
}
