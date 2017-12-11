package com.john.cena.controller;

import java.net.URI;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.john.cena.exception.IssueNotFoundException;
import com.john.cena.model.Issue;
import com.john.cena.service.IssueService;

@RestController
@RequestMapping(value = "/api/issues")
public class IssueController {
	
	private IssueService issueService;
	
	@Autowired
	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}

	/**
	 * 이슈 리스트
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getIssueList(Issue search) {
		return ResponseEntity.ok(issueService.getIssueList(search));
	}
	
	/**
	 * 이슈 한개
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getIssue(@PathVariable(value = "id") String id) {
		return ResponseEntity.ok(issueService.getIssue(id)
				.orElseThrow(
						() -> new IssueNotFoundException(id)));
	}
	
	/**
	 * 이슈 생성
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createIssue(@Valid @RequestBody Issue issue, Errors errors) {
		
		// 필수값 validation error 메세지 처리
		if (errors.hasErrors()) {
			String msg = 
					errors.getAllErrors()
					.stream()
					.map(x -> x.getDefaultMessage())
					.collect(Collectors.joining(","));
			
			return ResponseEntity.badRequest().body(msg);
		}
		
		Issue createIssue = issueService.createIssue(issue);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createIssue.getId()).toUri();
		
		return ResponseEntity.created(location).body(createIssue);
	}
	
	/**
	 * 이슈 수정
	 */
	@RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
	public ResponseEntity<?> updateIssue(@PathVariable(value = "id") String id, @Valid  @RequestBody Issue issue, Errors errors) {

		validateIssue(id);
		
		// 필수값 validation error 메세지 처리
		if (errors.hasErrors()) {
			String msg = 
					errors.getAllErrors()
					.stream()
					.map(x -> x.getDefaultMessage())
					.collect(Collectors.joining(","));
			
			return ResponseEntity.badRequest().body(msg);
		}
		
		issue.setId(id);
		return ResponseEntity.ok(issueService.updateIssue(issue));
	}
	
	/**
	 * 이슈 삭제
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> updateIssue(@PathVariable(value = "id") String id) {
		
		validateIssue(id);
		
		issueService.deleteIssue(id);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/**
	 * 이슈를 찾을 수 없을때 404 리턴
	 */
	private void validateIssue(String id) {
		issueService.getIssue(id).orElseThrow(
			() -> new IssueNotFoundException(id));
	}
}
