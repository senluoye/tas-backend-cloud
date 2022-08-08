package com.qks.common.dto.evaluation;

import lombok.Data;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-08 23:31
 */
@Data
public class ApplyDTO {
    private String loginName;
    private Integer status;
    private String name;
    private Integer type;
}
