package org.zpp.common.constant;

public interface BootCommonConstants {

	/**
	 * 默认的处理验证码的url前缀
	 */
	public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

	/**
	 * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
	/**
	 * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
	/**
	 * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

	/**
	 * 图片验证码 type /code/image
	 */
	public static final String IMAGE_CODE_TYPE = "image";

	/**
	 * 图片验证码宽度字段名
	 */
	public static final String IMAGE_CODE_WIDTH_NAME = "width";

	/**
	 * 图片验证码高度字段名
	 */
	public static final String IMAGE_CODE_HEIGHT_NAME = "height";

	/**
	 * 短信验证码 type /code/sms
	 */
	public static final String SMS_CODE_TYPE = "sms";

	/**
	 * 验证码请求头字段
	 */
	public static final String VALIDATE_CODE_HEADER_NAME = "deviceId";
}