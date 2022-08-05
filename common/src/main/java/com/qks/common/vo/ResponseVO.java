package com.qks.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 17:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseVO<T> implements Serializable {
    private T data;
    private Integer code;
    private String msg;
}
