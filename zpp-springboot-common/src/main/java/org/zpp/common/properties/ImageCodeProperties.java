package org.zpp.common.properties;

import lombok.Data;

/**
 * 验证码配置
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties {

    private int width = 67;

    private int height = 23;

    public ImageCodeProperties() {
        setLength(4);
    }


}