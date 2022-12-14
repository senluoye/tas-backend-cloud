package com.qks.openfeign.service;

import com.qks.common.po.User;
import com.qks.common.vo.ResponseVO;
import com.qks.openfeign.config.MyFeignRequestInterceptor;
import com.qks.openfeign.service.backimpl.JobBackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-06 00:04
 */
@FeignClient(value = "userservice", fallbackFactory = JobBackImpl.class, configuration = MyFeignRequestInterceptor.class)
public interface UserClient {

    @PostMapping("/api/user/check")
    ResponseVO<Boolean> isAdminOrLeadership(Integer userId);

    @PostMapping("/api/user/experts")
    ResponseVO<List<User>> getExpertListXML(@RequestParam("name") String name,
                                            @RequestParam("department") String department);

    @PostMapping("/api/user")
    ResponseVO<User> getUser(Integer userId);

    @PostMapping("/api/user/check/teacher")
    ResponseVO<Boolean> isTeacher(Integer userId);

    @PostMapping("/api/user/check/expert")
    ResponseVO<Boolean> isExpert(Integer integer);

    @PostMapping("/api/user/check/teacher/xml")
    ResponseVO<List<User>> getUserXML(@RequestParam("loginName") String loginName,
                                      @RequestParam("name") String name);
}
