package com.qks.common.po;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-11 13:36
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class JobNameNode extends Model{
    private String name;
    private Integer parentId;
    private Integer cnt;
}
