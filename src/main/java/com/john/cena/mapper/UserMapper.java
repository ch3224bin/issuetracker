package com.john.cena.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.john.cena.model.User;

@Mapper
public interface UserMapper {
	public List<User> selectUserList(User param);
	public User selectUser(User param);
	public void mergeUser(User param);
}
