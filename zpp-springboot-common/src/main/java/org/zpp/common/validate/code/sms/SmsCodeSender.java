package org.zpp.common.validate.code.sms;


public interface SmsCodeSender {
	
	void send(String mobile, String code);

}
