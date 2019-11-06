package org.zpp.springboot.oss.service;

import org.zpp.springboot.oss.common.OssCallbackResult;
import org.zpp.springboot.oss.common.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

public interface OssService {
    /**
     * oss上传策略生成
     */
    OssPolicyResult policy();

    /**
     * oss上传成功回调
     */
    OssCallbackResult callback(HttpServletRequest request);
}