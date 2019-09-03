package org.zpp.common.validate.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码
 *
 * @author zpp
 */
@Data
@AllArgsConstructor
public class ValidateCode implements Serializable {

	/**
	 * 验证码
	 */
	private String code;

	/**
	 * 有效期
	 */
	private LocalDateTime expireTime;

	public ValidateCode(String code, int expireIn){
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}

	public boolean isExpried() {
		return LocalDateTime.now().isAfter(expireTime);
	}
}
