package com.qks.common.dto.evaluation;

import lombok.Data;

import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-08 23:25
 */
@Data
public class EvaluateDTO {
    private Integer userId;
    private Integer jobId;
    private Integer status;
    private List<Integer> expertIdList;
}
