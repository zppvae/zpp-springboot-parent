package org.zpp.common.validate.code.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zpp.common.validate.code.ValidateCode;
import org.zpp.common.validate.code.impl.AbstractValidateCodeProcessor;

import java.io.OutputStream;


/**
 * 短信验证码处理器
 *
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

	/**
	 * 短信验证码发送器
	 */
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	@Override
	protected void send(OutputStream os, String mobile, ValidateCode validateCode) throws Exception {
		smsCodeSender.send(mobile, validateCode.getCode());
	}

}
