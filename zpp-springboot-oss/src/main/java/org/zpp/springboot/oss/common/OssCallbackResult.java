package org.zpp.springboot.oss.common;

import lombok.Data;

/**
 * oss上传文件的回调结果
 */
@Data
public class OssCallbackResult {

    private String filename;

    private String size;

    private String mimeType;

    private String width;

    private String height;
}