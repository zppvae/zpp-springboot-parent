package org.zpp.springboot.webflux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zpp.springboot.webflux.model.SysUser;
import org.zpp.springboot.webflux.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author zpp
 * @date 2019/8/27 17:30
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * onErrorResume，错误处理
     *
     * email 重复的数据通过userId更新email
     * @param user
     * @return
     */
    public Mono<SysUser> save(SysUser user) {
        return userRepository.save(user)
                .onErrorResume(e ->
                        userRepository.findByEmail(user.getEmail())
                                .flatMap(originalUser -> {
                                    user.setUserId(originalUser.getUserId());
                                    return userRepository.save(user);
                                }));
    }

    public Mono<SysUser> selectByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Flux<SysUser> selectAll() {
        return userRepository.findAll();
    }
}
