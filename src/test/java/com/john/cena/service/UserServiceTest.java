package com.john.cena.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.john.cena.mapper.UserMapper;
import com.john.cena.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
	@Autowired
	UserService userService;
	
	@Autowired
	UserMapper userMapper;
	
	@Test
	public void testRegistUser() {
		Map<String, String> userInfo = new LinkedHashMap<String, String>();
		userInfo.put("id", "12345");
		userInfo.put("nickname", "요그");
		userService.registUser(userInfo);
		
		User param = new User();
		param.setId("12345");
		User selectUser = userMapper.selectUser(param);
		assertThat(selectUser.getId(), equalTo("12345"));
		assertThat(selectUser.getNickname(), equalTo("요그"));
	}
}
