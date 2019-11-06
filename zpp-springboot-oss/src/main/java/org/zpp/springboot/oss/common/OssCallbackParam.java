package org.zpp.springboot.oss.common;

import lombok.Data;

/**
 * oss上传成功后的回调参数
 */
@Data
public class OssCallbackParam {

    private String callbackUrl;

    private String callbackBody;

    private String callbackBodyType;

}