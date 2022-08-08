package com.qks.openfeign.service;

import com.qks.common.vo.ResponseVO;
import com.qks.openfeign.service.backimpl.JobBackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-06 00:04
 */
@FeignClient(value = "userservice", fallback = JobBackImpl.class)
public interface UserClient {

    @PostMapping("/api/user/check")
    ResponseVO<Boolean> isAdminOrLeadership(Integer userId);

}
