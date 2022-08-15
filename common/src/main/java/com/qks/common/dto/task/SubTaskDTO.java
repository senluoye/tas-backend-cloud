package com.qks.common.dto.task;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-13 15:12
 */
@Data
@Builder
public class SubTaskDTO {
    private Integer parentTaskId;
    private String name;
    private String description;
}
