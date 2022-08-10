package com.qks.common.vo;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-08 23:38
 */
@Data
@Builder
public class AdminTasks {
    private Integer id;
    private String loginName;
    private String name;
    private String during;
    private Integer status;
    private Timestamp createAt;
    private Integer taskId;
    private Integer userJobRelationId;
}
