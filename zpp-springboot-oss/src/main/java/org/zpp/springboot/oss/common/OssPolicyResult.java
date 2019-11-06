package org.zpp.springboot.oss.common;

import lombok.Data;

/**
 * 获取OSS上传文件授权返回结果
 */
@Data
public class OssPolicyResult {

    /**
     * 访问身份验证中用到用户标识
     */
    private String accessKeyId;

    /**
     * 访问身份验证中用到用户标识
     */
    private String policy;

    /**
     * 对policy签名后的字符串
     */
    private String signature;

    private String dir;

    private String host;

    private String callback;


}