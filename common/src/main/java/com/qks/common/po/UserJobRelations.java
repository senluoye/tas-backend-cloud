package com.qks.common.po;

import lombok.Data;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:22
 */
@Data
public class UserJobRelations extends Model {
    private Integer userId;
    private Integer jobId;
    private Integer status;
}
