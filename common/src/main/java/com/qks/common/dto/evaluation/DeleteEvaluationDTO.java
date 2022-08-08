package com.qks.common.dto.evaluation;

import lombok.Data;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-08 23:41
 */
@Data
public class DeleteEvaluationDTO {
    private Integer expertId;
    private Integer userId;
    private Integer jobId;
}
