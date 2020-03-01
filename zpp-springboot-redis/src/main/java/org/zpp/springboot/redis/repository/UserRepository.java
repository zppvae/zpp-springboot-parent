package org.zpp.springboot.redis.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zpp.springboot.redis.model.User;

@CacheConfig(cacheNames = "users")
public interface UserRepository extends JpaRepository<User, Integer> {

    @Cacheable(key = "#p0")
    User findByUserName(String userName);

    /**
     * 更新操作同步到缓存
     * @param user
     * @return
     */
    @CachePut(key = "#p0.userName")
    User save(User user);
}