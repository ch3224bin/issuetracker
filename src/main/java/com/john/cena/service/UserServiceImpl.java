package com.john.cena.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

	/**
	 * 현재 접속자를 DB의 사용자 테이블에 insert or update
	 */
	public void registCurrentUser() {
		Map<String, String> userInfo = getCurrentUserInfo();
		User param = new User();
		param.setId(userInfo.get("id"));
		param.setNickname(userInfo.get("nickname"));
		userMapper.mergeUser(param);
	}
	
	/**
	 * 현재 접속자 정보 얻기.
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getCurrentUserInfo() {
		OAuth2Authentication authentication = (OAuth2Authentication)SecurityContextHolder.getContext().getAuthentication();
		return Optional.ofNullable(authentication)
				.map(OAuth2Authentication::getUserAuthentication)
				.map(Authentication::getDetails)
				.map(obj -> (Map<String, Map<String, String>>)obj)
				.map(m -> m.get("response"))
				.orElse(new HashMap<String, String>());
	}
}
