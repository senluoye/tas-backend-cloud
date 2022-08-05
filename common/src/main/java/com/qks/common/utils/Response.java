package com.qks.common.utils;

import com.qks.common.vo.ResponseVO;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-05 17:55
 */
public class Response {
    public static <T> ResponseVO<T> success(T data, Integer code, String msg) {
        return new ResponseVO<T>(data, code, msg);
    }

    public static <T> ResponseVO<T> success(T data) {
        return new ResponseVO<T>(data, 0, "success");
    }

    public static <T> ResponseVO<T> success(T data, String msg) {
        return new ResponseVO<T>(data, 0, msg);
    }

    public static <T> ResponseVO<T> error(Integer code, String msg) {
        return new ResponseVO<T>(null, code, msg);
    }
}
