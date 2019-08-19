package org.zpp.springboot.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.zpp.springboot.mybatis.wrapper.BodyReaderRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class CommonInterceptor extends HandlerInterceptorAdapter {

    private String getParamString(Map<String, String[]> map) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String[]> e : map.entrySet()) {
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                sb.append(value[0]).append("\t");
            } else {
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        if (handler instanceof HandlerMethod) {
            String uuid = UUID.randomUUID().toString();
            request.setAttribute("uuid", uuid);
            String method = request.getMethod();
            log.info("[{}] - [请求URL] - {}", uuid,request.getRequestURL());
            log.info("[{}] - [请求方法] - {}", uuid,((HandlerMethod) handler).getMethod().getName());
            log.info("[{}] - [请求方式] - {}", uuid,method);
            if (method.toLowerCase().equals(HttpMethod.GET)) {
                log.info("[{}] - [请求参数] - {}", uuid,getParamString(request.getParameterMap()));
            } else {
                log.info("[{}] - [请求参数] - {}", uuid,new BodyReaderRequestWrapper(request).getBodyString());
            }
            log.info("[{}] - [controller] - {}", uuid,((HandlerMethod) handler).getBean().getClass().getName());
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long startTime = (Long) request.getAttribute("startTime");
        Long endTime = System.currentTimeMillis();
        Long costTime = endTime - startTime;
        if (handler instanceof HandlerMethod) {
            log.info("[{}] - [请求耗时] - {}", request.getAttribute("uuid"), costTime);
        }
    }

}