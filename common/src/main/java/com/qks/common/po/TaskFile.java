package com.qks.common.po;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:28
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class TaskFile extends Model{
    private Integer userId;
    private Integer taskId;
    private String name;
    private String minioPath;
}
