package com.qks.common.po;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:19
 */
@Data
@Builder
public class UserRoleRelations {
    private Integer userId;
    private Integer roleId;
}
