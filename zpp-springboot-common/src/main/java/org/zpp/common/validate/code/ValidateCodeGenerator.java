package org.zpp.common.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成器
 *
 * 以增量的方式适应变化
 */
public interface ValidateCodeGenerator {

	ValidateCode generate(ServletWebRequest request);

}
