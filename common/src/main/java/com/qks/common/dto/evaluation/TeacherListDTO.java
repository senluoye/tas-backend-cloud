package com.qks.common.dto.evaluation;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-09 23:00
 */
@Data
@Builder
public class TeacherListDTO {
    private String loginName;
    private String name;
    private String type;
}
