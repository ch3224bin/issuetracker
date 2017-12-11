package com.john.cena.service;

import java.util.List;

import com.john.cena.model.Code;

public interface CodeService {
	public List<Code> getCodeList(String codeGroup);
}
