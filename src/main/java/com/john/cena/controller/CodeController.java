package com.john.cena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.john.cena.service.CodeService;

@RestController
public class CodeController {
	
	private CodeService codeService;
	
	@Autowired
	public void setCodeService(CodeService codeService) {
		this.codeService = codeService;
	}

	@RequestMapping(value = "/api/codegroups/{codeGroup}/codes", method = RequestMethod.GET)
	public ResponseEntity<?> getCodeList(@PathVariable(value = "codeGroup") String codeGroup) {
		return ResponseEntity.ok(codeService.getCodeList(codeGroup));
	}
}
