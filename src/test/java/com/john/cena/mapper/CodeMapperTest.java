package com.john.cena.mapper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.john.cena.model.Code;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeMapperTest {

	@Autowired
	CodeMapper codeMapper;
	
	@Test
	public void testSelectCodeList() {
		Code param = new Code();
		param.setCodeGroup("PRIORITY");
		param.setLang("ko");
		List<Code> selectCodeList = codeMapper.selectCodeList(param);
		assertThat(selectCodeList.size(), equalTo(3));
		assertThat(selectCodeList.get(0).getLabel(), equalTo("매우중요"));
	}
}
