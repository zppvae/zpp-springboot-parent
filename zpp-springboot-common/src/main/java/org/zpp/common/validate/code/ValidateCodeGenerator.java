package org.zpp.common.validate.code;


/**
 * 验证码生成器
 *
 * 以增量的方式适应变化
 */
public interface ValidateCodeGenerator {

	/**
	 * 生成验证码
	 * @param width
	 * 			验证码宽度（图片）
	 * @param height
	 * 			验证码高度（图片）
	 * @return
	 */
	ValidateCode generate(int width,int height);

}
