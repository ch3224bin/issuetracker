package com.john.cena.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getCurrentUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, String> userInfo = new HashMap<String, String>();
		OAuth2Authentication auth = (OAuth2Authentication)authentication;
		if (auth != null) {
			Map<String, Map<String, String>> map = (Map<String, Map<String, String>>)auth.getUserAuthentication().getDetails();
			if (map != null) {
				userInfo = map.get("response");
			}
		}
		return userInfo;
	}
}
