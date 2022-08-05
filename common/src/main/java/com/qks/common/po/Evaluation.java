package com.qks.common.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Evaluation extends Model{
    private Integer userJobRelationsId;
    private Integer expertId;
    private String comment;
    private Integer status;
}
