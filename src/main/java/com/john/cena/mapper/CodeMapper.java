package com.john.cena.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.john.cena.model.Code;

@Mapper
public interface CodeMapper {
	public List<Code> selectCodeList(Code param);
	public Code selectCode(Code param);
}
