package org.zpp.common.validate.code.image;

import org.springframework.stereotype.Component;
import org.zpp.common.validate.code.impl.AbstractValidateCodeProcessor;

import javax.imageio.ImageIO;
import java.io.OutputStream;

/**
 * 图片验证码处理器
 * 
 * @author zpp
 *
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

	/**
	 * 发送图形验证码，将其写到响应中
	 */
	@Override
	protected void send(OutputStream os, String mobile, ImageCode imageCode) throws Exception {
		ImageIO.write(imageCode.getImage(), "JPEG", os);
	}

}
