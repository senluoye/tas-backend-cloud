package com.qks.common.po;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:24
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Job extends Model {
    private Integer Rank;
    private String jobType;
    private String jobLevelName;
    private String jobPositionTarget;
    private String jobDoctorTarget;
    private String during;
    private Integer applyTaskID;
    private Integer appraisalTaskID;
    private Integer status;
}
