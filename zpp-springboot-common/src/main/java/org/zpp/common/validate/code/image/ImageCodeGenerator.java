package org.zpp.common.validate.code.image;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.zpp.common.properties.ValidateCodeProperties;
import org.zpp.common.validate.code.ValidateCodeGenerator;

/**
 *
 * 图片验证码生成器
 * @author zpp
 */
@Slf4j
public class ImageCodeGenerator implements ValidateCodeGenerator {

	/**
	 * 系统级配置
	 */
	@Autowired
	@Getter
	@Setter
	private ValidateCodeProperties validateCodeProperties;
	
	@Override
	public ImageCode generate(ServletWebRequest request) {
		int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width",
				validateCodeProperties.getImage().getWidth());
		int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",
				validateCodeProperties.getImage().getHeight());

		//定义图形验证码的长、宽、验证码字符数、干扰元素个数
		CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(width,height,
				validateCodeProperties.getImage().getLength(), 20);

		log.info("[imageCode] - [{}]",captcha.getCode());
		ImageCode imageCode = new ImageCode(captcha.getImage(),captcha.getCode(),
				validateCodeProperties.getImage().getExpireIn());
		return imageCode;
	}
	

}
