package org.zpp.springboot.redis.interceptor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.zpp.springboot.redis.annotation.ApiIdempotent;
import org.zpp.springboot.redis.service.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

@Component
public class ApiIdempotentInterceptor implements HandlerInterceptor{

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        ApiIdempotent annotation = method.getAnnotation(ApiIdempotent.class);
        if (annotation  != null)  {
            try {
                return tokenService.checkToken(request);
            }catch (Exception e) {
                write(response,"重复操作");
                throw e;
            }
        }
        return true;
    }

    private void write(HttpServletResponse response, String str) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(str);
        } catch (IOException e) {

        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
