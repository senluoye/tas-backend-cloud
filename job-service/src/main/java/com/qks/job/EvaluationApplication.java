package com.qks.job;

import com.qks.openfeign.config.DefaultFeignConfiguration;
import com.qks.openfeign.config.MyRulerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-08 19:29
 */
@EnableFeignClients(basePackages = "com.qks.openfeign", defaultConfiguration = DefaultFeignConfiguration.class)
@RibbonClient(name = "evaluationservice", configuration = MyRulerConfig.class)
@EnableDiscoveryClient
@SpringBootApplication
public class EvaluationApplication {
    public static void main(String[] args) {
        SpringApplication.run(EvaluationApplication.class, args);
    }
}
