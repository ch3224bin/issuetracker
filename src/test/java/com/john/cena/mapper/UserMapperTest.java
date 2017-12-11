package com.john.cena.mapper;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.john.cena.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
	@Autowired
	UserMapper userMapper;

	@Test
	public void testReadUser() {
		List<User> selectUser = userMapper.selectUserList(new User());
		assertThat(selectUser.size(), equalTo(5));
	}
}
