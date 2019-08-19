package org.zpp.springboot.es.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zpp.springboot.es.model.UserEntity;
import org.zpp.springboot.es.reposiory.UserReposiory;

import java.util.Optional;

@RestController
public class EsController {

	@Autowired
	private UserReposiory userReposiory;

	/**
	 * 新增文档
	 * @param user
	 * @return
	 */
	@PostMapping("/user")
	public UserEntity addUser(@RequestBody UserEntity user) {
		return userReposiory.save(user);
	}

	@GetMapping("/user")
	public Optional<UserEntity> findUser(String id) {
		return userReposiory.findById(id);
	}

}