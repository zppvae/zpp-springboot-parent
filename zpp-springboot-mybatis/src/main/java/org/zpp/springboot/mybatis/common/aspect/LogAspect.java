package org.zpp.springboot.mybatis.common.aspect;

import java.util.Date;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.zpp.springboot.mybatis.common.annotation.Log;
import org.zpp.springboot.mybatis.common.event.SysLogEvent;
import org.zpp.springboot.mybatis.model.SysLog;
import org.zpp.springboot.mybatis.service.SpringContextHolder;
import org.zpp.springboot.mybatis.service.SysLogService;

@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * get请求获取参数：request.getParameterMap()
     *
     * @param point
     * @param logger
     * @return
     * @throws Throwable
     */
    @Around("@annotation(logger)")
    public Object around(ProceedingJoinPoint point,Log logger) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;

        MethodSignature signature = (MethodSignature) point.getSignature();
        // 获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String url = URLUtil.getPath(request.getRequestURI());
        // 请求的方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        String ip = ServletUtil.getClientIP(request);
        Map<String,String[]> map = request.getParameterMap();

//        String params = HttpUtil.toParams(request.getParameterMap());

        String params = HttpUtil.getString(request.getInputStream(),null,true);
        log.info("[请求URL] - [{}]",url);
        log.info("[请求方法] - [{}]",methodName);
        log.info("[请求IP] - [{}]",ip);
        log.info("[请求参数] - [{}]",params);
        log.info("[请求处理时间] - [{}]",(int)time);

        SysLog sysLog = new SysLog();
        sysLog.setOperation(logger.value());
        sysLog.setMethod(className + "." + methodName + "()");
        sysLog.setIp(ip);
        sysLog.setTime((int)time);
        sysLog.setCreateTime(new Date());
        sysLog.setParams(params);
        sysLog.setUserAgent(request.getHeader("user-agent"));
        sysLog.setRequestUri(url);

        SpringContextHolder.publishEvent(new SysLogEvent(sysLog));
        return result;
    }


}