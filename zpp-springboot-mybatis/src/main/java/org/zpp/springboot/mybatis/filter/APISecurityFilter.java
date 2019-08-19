package org.zpp.springboot.mybatis.filter;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 防篡改、防重放过滤器
 * @author zpp
 * @date 2019/8/19 15:46
 */
@Slf4j
@Component
@WebFilter
public class APISecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init aPISecurityFilter");
    }

    @Override
    public void doFilter(ServletRequest args0, ServletResponse args1, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) args0;
        // 获取token
        String token = request.getHeader("token");
        // 获取时间戳
        Long timestamp = Long.valueOf(request.getHeader("timestamp"));
        // 获取随机字符串
        String nonceStr = request.getHeader("nonceStr");
        // 获取请求地址
        String url = request.getHeader("url");
        // 获取签名
        String signature = request.getHeader("signature");

        // 判断参数是否为空
//        if (StringUtils.isBlank(token) || StringUtils.isBlank(timestamp)
//                || StringUtils.isBlank(nonceStr) || StringUtils.isBlank(url)
//                || StringUtils.isBlank(signature)) {
//            //非法请求
//            return;
//        }

        //验证token有效性

        // 判断请求的url参数是否正确
//        if (!request.getRequestURI().equals(url)){
//            //非法请求 (防止跨域攻击)
//            return;
//        }

        // 判断时间是否大于60秒
//        long diff = DateUtil.between(DateUtil.date(), DateUtil.date(timestamp * 1000), DateUnit.SECOND);
//        if(diff > 60){
//            //请求超时(防止重放攻击)
//            return;
//        }

        // 判断该用户的nonceStr参数是否已经在redis中
//        if (RedisUtils.haveNonceStr(userId,nonceStr)){
//            //请求仅一次有效（防止短时间内的重放攻击）
//            return;
//        }

        // 对请求头参数进行签名
//        String stringB = SignUtil.signature(token, timestamp, nonceStr, url,request);

        // 如果签名验证不通过
//        if (!signature.equals(stringB)) {
//            //非法请求（防止请求参数被篡改）
//            return;
//        }

        chain.doFilter(args0,args1);
    }

    @Override
    public void destroy() {

    }
}
