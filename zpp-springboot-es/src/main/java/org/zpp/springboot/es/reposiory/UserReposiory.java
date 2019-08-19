package org.zpp.springboot.es.reposiory;

import org.springframework.data.repository.CrudRepository;
import org.zpp.springboot.es.model.UserEntity;

public interface UserReposiory extends CrudRepository<UserEntity, String> {

}