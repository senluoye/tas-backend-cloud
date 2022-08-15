package com.qks.openfeign.config;

import com.qks.openfeign.service.backimpl.EvaluationBackImpl;
import com.qks.openfeign.service.backimpl.JobBackImpl;
import com.qks.openfeign.service.backimpl.TaskBackImpl;
import com.qks.openfeign.service.backimpl.UserBackImpl;
import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName 注册日志类，以及降级类
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-06-10 16:25
 */
public class DefaultFeignConfiguration {

    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.BASIC;
    }

    @Bean
    public EvaluationBackImpl evaluationBack() {
        return new EvaluationBackImpl();
    }

    @Bean
    public JobBackImpl jobBack() {
        return new JobBackImpl();
    }

    @Bean
    public TaskBackImpl taskBack() {
        return new TaskBackImpl();
    }

    @Bean
    public UserBackImpl userBack() {
        return new UserBackImpl();
    }
}
