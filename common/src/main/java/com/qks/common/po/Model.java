package com.qks.common.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
public class Model {
    private Integer id;
    private Timestamp createAt;
    private Timestamp updateAt;
    private Timestamp deleteAt;
}
