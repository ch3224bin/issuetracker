package com.john.cena.service;

import java.util.List;
import java.util.Map;

import com.john.cena.model.User;

public interface UserService {
	public void registUser(Map<String, String> userInfo);
	public List<User> getUserList(User param);
}
