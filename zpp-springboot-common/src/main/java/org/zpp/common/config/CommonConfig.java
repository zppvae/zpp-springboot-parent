package org.zpp.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.zpp.common.properties.ValidateCodeProperties;

/**
 * @author zpp
 * @date 2019/9/3 17:20
 */
@Configuration
@EnableConfigurationProperties(ValidateCodeProperties.class)
public class CommonConfig {
}
