package com.john.cena.service;

import java.util.List;
import java.util.Map;

import com.john.cena.model.User;

public interface UserService {
	public void registCurrentUser();
	public List<User> getUserList(User param);
	public Map<String, String> getCurrentUserInfo();
}
