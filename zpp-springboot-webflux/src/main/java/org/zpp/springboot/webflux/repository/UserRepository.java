package org.zpp.springboot.webflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.zpp.springboot.webflux.model.SysUser;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<SysUser, String> {

    /**
     * 根据 邮箱 查询
     * @param email
     * @return
     */
    Mono<SysUser> findByEmail(String email);

}