package org.zpp.common.validate.code;


public interface ValidateCodeRepository {

    /**
     * 保存验证码
     *
     * @param deviceId
     * @param code
     * @param validateCodeType
     */
    void save(String deviceId, ValidateCode code, ValidateCodeType validateCodeType);

    /**
     * 获取验证码
     *
     * @param deviceId
     * @param validateCodeType
     * @return
     */
    ValidateCode get(String deviceId, ValidateCodeType validateCodeType);

    /**
     * 移除验证码
     *
     * @param deviceId
     * @param codeType
     */
    void remove(String deviceId, ValidateCodeType codeType);

}
