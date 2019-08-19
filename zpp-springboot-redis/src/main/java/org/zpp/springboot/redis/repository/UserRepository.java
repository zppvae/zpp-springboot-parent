package org.zpp.springboot.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zpp.springboot.redis.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}