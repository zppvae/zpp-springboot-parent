package org.zpp.springboot.mybatis.filter;

import lombok.extern.slf4j.Slf4j;
import org.zpp.springboot.mybatis.wrapper.BodyReaderRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 请求过滤器
 * @author zpp
 */
@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //将请求流继续向下传递
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        ServletRequest requestWrapper = new BodyReaderRequestWrapper(httpServletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);
    }
 
    @Override
    public void destroy() {
 
    }
}