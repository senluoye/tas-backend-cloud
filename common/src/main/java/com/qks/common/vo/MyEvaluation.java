package com.qks.common.vo;

import com.qks.common.po.Evaluation;
import com.qks.common.po.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-09 13:27
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class MyEvaluation extends Model {
    private Integer userJobRelationsId;
    private Integer expertId;
    private String comment;
    private Integer status;
    private String expertName;
}
