package org.springframework.web.debug.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.debug.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/user")
public class UserController {

	private static Map<Integer, User> users = new HashMap<>();

	private static AtomicInteger idGenerator = new AtomicInteger();

	@PostMapping("/create")
	public User create(@RequestBody User user) {
		user.setId(idGenerator.incrementAndGet());
		users.put(user.getId(), user);
		return user;
	}

	@GetMapping("/{id}")
	public User getById(@PathVariable("id") int id) {
		return users.get(id);
	}

	@GetMapping("/")
	public String health() {
		return "OK";
	}

}