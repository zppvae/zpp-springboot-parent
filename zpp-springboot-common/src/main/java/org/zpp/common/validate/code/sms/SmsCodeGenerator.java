package org.zpp.common.validate.code.sms;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.zpp.common.properties.ValidateCodeProperties;
import org.zpp.common.validate.code.ValidateCode;
import org.zpp.common.validate.code.ValidateCodeGenerator;


@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	private ValidateCodeProperties validateCodeProperties;

	@Override
	public ValidateCode generate(ServletWebRequest request) {
		String code = RandomStringUtils.randomNumeric(validateCodeProperties.getSms().getLength());
		return new ValidateCode(code, validateCodeProperties.getSms().getExpireIn());
	}

	public ValidateCodeProperties getValidateCodeProperties() {
		return validateCodeProperties;
	}

	public void setValidateCodeProperties(ValidateCodeProperties validateCodeProperties) {
		this.validateCodeProperties = validateCodeProperties;
	}
}
