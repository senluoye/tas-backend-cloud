package com.qks.user.config;

import com.qks.common.exception.ServiceException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName Dessert
 * @Description
 * @Author QKS
 * @Version v1.0
 * @Create 2022-08-15 20:08
 */
public class MyHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String flag = request.getHeader("flag");
        if (!"flag".equals(flag)) {
            throw new ServiceException("没有权限进行请求");
        }
        
        return true;
    }
}
