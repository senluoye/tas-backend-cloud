package com.qks.common.po;

import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 18:00
 */
@Data
@Builder
public class Model {
    private Integer id;
    private Timestamp createAt;
    private Timestamp updateAt;
    private Timestamp deleteAt;
}
