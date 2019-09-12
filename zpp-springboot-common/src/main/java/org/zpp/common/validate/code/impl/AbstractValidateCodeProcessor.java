package org.zpp.common.validate.code.impl;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.zpp.common.constant.BootCommonConstants;
import org.zpp.common.exception.ValidateCodeException;
import org.zpp.common.properties.ValidateCodeProperties;
import org.zpp.common.validate.code.*;

import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;


public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;

	@Autowired
	private ValidateCodeRepository validateCodeRepository;

	@Autowired
	private ValidateCodeProperties validateCodeProperties;

	@Override
	public void create(ServletWebRequest request,String codeType) throws Exception {
		int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width",
				validateCodeProperties.getImage().getWidth());
		int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",
				validateCodeProperties.getImage().getHeight());

		String deviceId = request.getHeader(BootCommonConstants.VALIDATE_CODE_HEADER_NAME);
		String mobile = null;
		if (codeType.equals(BootCommonConstants.SMS_CODE_TYPE)) {
			String paramName = BootCommonConstants.DEFAULT_PARAMETER_NAME_MOBILE;
			mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), paramName);
		}
		C validateCode = generate(width,height);
		save(deviceId, validateCode);
		send(request.getResponse().getOutputStream(),mobile, validateCode);
	}

	@Override
	public void createToServerRequest(ServerRequest serverRequest,OutputStream os) throws Exception {
		int width = validateCodeProperties.getImage().getWidth();
		int height = validateCodeProperties.getImage().getHeight();

		Optional<String> widthOp = serverRequest.queryParam(BootCommonConstants.IMAGE_CODE_WIDTH_NAME);
		Optional<String> heightOp = serverRequest.queryParam(BootCommonConstants.IMAGE_CODE_HEIGHT_NAME);
		if (widthOp.isPresent()) {
			String widthStr = widthOp.get();
			if (StringUtils.isNotBlank(widthStr)) {
				width = Integer.valueOf(widthStr);
			}
		}
		if (heightOp.isPresent()) {
			String heightStr = heightOp.get();
			if (StringUtils.isNotBlank(heightStr)) {
				height = Integer.valueOf(heightStr);
			}
		}

		String deviceId = serverRequest.headers().header(BootCommonConstants.VALIDATE_CODE_HEADER_NAME).get(0);

		String paramName = BootCommonConstants.DEFAULT_PARAMETER_NAME_MOBILE;
        Optional<String> mobileOp =serverRequest.queryParam(paramName);
        String mobile = null;
        if (mobileOp.isPresent()) {
            mobile = mobileOp.get();
        }
		C validateCode = generate(width,height);
		save(deviceId, validateCode);
		send(os,mobile, validateCode);
	}

	/**
	 * 生成校验码
	 * @param width
	 * @param height
	 * @return
	 */
	private C generate(int width,int height) {
		String type = getValidateCodeType().toString().toLowerCase();
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (C) validateCodeGenerator.generate(width,height);
	}

	/**
	 * 保存校验码
	 * 
	 * @param deviceId
	 * @param validateCode
	 */
	private void save(String deviceId, C validateCode) {
		ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
		validateCodeRepository.save(deviceId, code, getValidateCodeType());
	}


	/**
	 * 发送校验码，由子类实现
	 * @param os
	 * 			图片验证码输出流
	 * @param mobile
	 * 			手机号（短信验证码）
	 * @param validateCode
	 * @throws Exception
	 */
	protected abstract void send(OutputStream os,String mobile, C validateCode) throws Exception;

	/**
	 * 根据请求的url获取校验码的类型
	 * 
	 * @return
	 */
	private ValidateCodeType getValidateCodeType() {
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void validate(String deviceId,String codeVal) {
		ValidateCodeType codeType = getValidateCodeType();

		C codeInSession = (C) validateCodeRepository.get(deviceId, codeType);


		if (StringUtils.isBlank(codeVal)) {
			throw new ValidateCodeException(codeType + "验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException(codeType + "验证码不存在");
		}

		if (codeInSession.isExpried()) {
			validateCodeRepository.remove(deviceId, codeType);
			throw new ValidateCodeException(codeType + "验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeVal)) {
			throw new ValidateCodeException(codeType + "验证码不匹配");
		}

		validateCodeRepository.remove(deviceId, codeType);
	}

}
