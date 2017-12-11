package com.john.cena.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.john.cena.model.User;
import com.john.cena.service.UserService;

@RestController
public class UserController {
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.GET)
	public ResponseEntity<?> user(Principal principal) {
		return ResponseEntity.ok(principal);
	}
	
	@RequestMapping(value = "/api/users", method = RequestMethod.GET)
	public ResponseEntity<?> users(User param) {
		return ResponseEntity.ok(userService.getUserList(param));
	}
}
