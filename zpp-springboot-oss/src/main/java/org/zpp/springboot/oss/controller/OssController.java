package org.zpp.springboot.oss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zpp.springboot.oss.common.OssCallbackResult;
import org.zpp.springboot.oss.common.OssPolicyResult;
import org.zpp.springboot.oss.service.OssServiceImpl;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/aliyun/oss")
public class OssController {

    @Autowired
    private OssServiceImpl ossService;

    /**
     * 生成oss上传签名
     * @return
     */
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public OssPolicyResult policy() {
        OssPolicyResult result = ossService.policy();
        return result;
    }

    /**
     * 文件上传成功回调接口
     * @param request
     * @return
     */
    @RequestMapping(value = "callback", method = RequestMethod.POST)
    @ResponseBody
    public OssCallbackResult callback(HttpServletRequest request) {
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        return ossCallbackResult;
    }

}