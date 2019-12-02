package org.zpp.springboot.es.reposiory;

import org.springframework.data.repository.CrudRepository;
import org.zpp.springboot.es.model.User;

public interface UserReposiory extends CrudRepository<User, String> {

}