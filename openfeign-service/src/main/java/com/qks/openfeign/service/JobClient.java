package com.qks.openfeign.service;

import com.qks.openfeign.service.backimpl.JobBackImpl;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-06 00:08
 */
@FeignClient(value = "jobservice", fallback = JobBackImpl.class)
public interface JobClient {

}
