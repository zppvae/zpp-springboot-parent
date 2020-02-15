package org.zpp.springboot.redis.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.zpp.springboot.redis.interceptor.ApiIdempotentInterceptor;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter{

    @Autowired
    private ApiIdempotentInterceptor apiIdempotentInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiIdempotentInterceptor);
        super.addInterceptors(registry);
    }
}
