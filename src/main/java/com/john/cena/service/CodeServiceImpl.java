package com.john.cena.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.cena.mapper.CodeMapper;
import com.john.cena.model.Code;

@Service
public class CodeServiceImpl implements CodeService {
	
	private CodeMapper codeMapper;

	@Autowired
	public void setCodeMapper(CodeMapper codeMapper) {
		this.codeMapper = codeMapper;
	}

	public List<Code> getCodeList(String codeGroup) {
		Code param = new Code();
		param.setCodeGroup(codeGroup);
		param.setLang("en");
		return codeMapper.selectCodeList(param);
	}

}
