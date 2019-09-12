package org.zpp.common.validate.code;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.io.OutputStream;

/**
 * 校验码处理器，封装不同校验码的处理逻辑
 * 
 * @author zpp
 *
 */
public interface ValidateCodeProcessor {

	/**
	 * 验证码放入session时的前缀
	 */
	String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

	/**
	 * 创建校验码
	 * 
	 * @param request
	 * @throws Exception
	 */
	void create(ServletWebRequest request,String codeType) throws Exception;

	/**
	 * 创建校验码
	 * @param serverRequest
	 */
	void createToServerRequest(ServerRequest serverRequest, OutputStream os) throws Exception;

	/**
	 * 校验验证码
	 * @param deviceId
	 * 			验证码存储key（验证码为唯一标识）
	 * @param codeVal
	 * 			验证码的值
	 */
	void validate(String deviceId,String codeVal);

}
