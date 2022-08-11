package com.qks.common.po;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-11 13:36
 */
@Data
@Builder
public class JobNameNode extends Model{
    private String name;
    private Integer parentId;
    private Integer cnt;
}
