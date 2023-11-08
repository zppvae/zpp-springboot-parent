package org.zpp.springboot.redis.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zpp.springboot.redis.constants.RedisConstant;

import javax.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    private RedisService redisService;

    @Override
    public String createToken() {
        String str = RandomUtil.randomString(10);
        StringBuilder token = new StringBuilder();
        try {
            token.append(RedisConstant.TOKEN)
                    .append(str);
            redisService.set(token.toString(), token.toString(), 10000L);
            boolean notEmpty = StrUtil.isNotEmpty(token.toString());
            if (notEmpty) {
                return token.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean checkToken(HttpServletRequest request) throws Exception {
        String token = request.getHeader(RedisConstant.HEADER_TOKEN);
        if (StrUtil.isBlank(token)) {
            token = request.getParameter(RedisConstant.HEADER_TOKEN);
            if (StrUtil.isBlank(token)) {
                throw new RuntimeException("token不能为空");
            }
        }
        if (!redisService.exists(token)) {
            throw new RuntimeException("token不存在");
        }
        redisService.remove(token);
        return true;
    }
}
