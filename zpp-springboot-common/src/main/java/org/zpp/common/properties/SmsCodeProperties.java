package org.zpp.common.properties;

import lombok.Data;

@Data
public class SmsCodeProperties {
	
	private int length = 6;
	private int expireIn = 60;

	/**
	 * 拦截的url
	 */
	private String url;
}