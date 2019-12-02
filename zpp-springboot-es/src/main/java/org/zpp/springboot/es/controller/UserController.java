package org.zpp.springboot.es.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zpp.springboot.es.model.User;
import org.zpp.springboot.es.reposiory.UserReposiory;

import java.util.Optional;

@RestController
public class UserController {

	@Autowired
	private UserReposiory userReposiory;


	@PostMapping("/user")
	public User addUser(@RequestBody User user) {
		return userReposiory.save(user);
	}

	@GetMapping("/user")
	public Optional<User> findUser(String id) {
		return userReposiory.findById(id);
	}

}