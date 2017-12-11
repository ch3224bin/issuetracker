package com.john.cena.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.cena.mapper.UserMapper;
import com.john.cena.model.User;

@Service
public class UserServiceImpl implements UserService {

	private UserMapper userMapper;
	
	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	public List<User> getUserList(User param) {
		return userMapper.selectUserList(param);
	}

	public void registUser(Map<String, String> userInfo) {
		User param = new User();
		param.setId(userInfo.get("id"));
		param.setNickname(userInfo.get("nickname"));
		userMapper.mergeUser(param);
	}
}
