package org.zpp.springboot.mybatis.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * sql注入
 *
 * @author zpp
 */
@Order(1)
@Component
@WebFilter
@Slf4j
public class AntiSqlInjectionfilter implements Filter {


    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest args0, ServletResponse args1,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) args0;
        HttpServletResponse res = (HttpServletResponse) args1;

        String uri = req.getRequestURI();

        //获得所有请求参数名
        Enumeration params = req.getParameterNames();
        while (params.hasMoreElements()) {
            //得到参数名
            String name = params.nextElement().toString();
            //得到参数对应值
            String[] value = req.getParameterValues(name);
            for (int i = 0; i < value.length; i++) {
                if (sqlValidate(value[i])) {
                    throw new IOException("您发送请求中的参数中含有非法字符");
                }
            }
        }

        chain.doFilter(args0, args1);
    }

    //效验
    protected static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        String badStr = "'|and|exec|execute|select|delete|update|count|drop|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|like'|insert|create|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|order|by|*|" +
                "or|;|--|+|like|//|/|#";//过滤掉的sql关键字，可以手动添加

        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            String s = badStrs[i];
            if (str.indexOf(s) >= 0 && !"password".equals(str)) {
                return true;
            }
        }
        return false;
    }

}
