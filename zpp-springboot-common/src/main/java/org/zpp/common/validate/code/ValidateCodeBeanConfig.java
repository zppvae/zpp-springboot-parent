package org.zpp.common.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zpp.common.properties.ValidateCodeProperties;
import org.zpp.common.validate.code.image.ImageCodeGenerator;
import org.zpp.common.validate.code.sms.DefaultSmsCodeSender;
import org.zpp.common.validate.code.sms.SmsCodeSender;

/**
 *
 * 验证码配置
 *
 * @author zpp
 */
@Configuration
public class ValidateCodeBeanConfig {
	
	@Autowired
	private ValidateCodeProperties validateCodeProperties;

	@Bean
	@ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator() {
		ImageCodeGenerator codeGenerator = new ImageCodeGenerator();
		codeGenerator.setValidateCodeProperties(validateCodeProperties);
		return codeGenerator;
	}

	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}

}