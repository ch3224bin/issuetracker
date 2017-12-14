package com.john.cena.controller;

import java.net.URI;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.h2.util.StringUtils;
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

import com.john.cena.exception.CommentNotFoundException;
import com.john.cena.exception.IssueNotFoundException;
import com.john.cena.model.Comment;
import com.john.cena.model.Issue;
import com.john.cena.service.CommentService;
import com.john.cena.service.IssueService;

@RestController
@RequestMapping(value = "/api/issues")
public class IssueController {
	
	private IssueService issueService;
	private CommentService commentService;

	@Autowired
	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}
	
	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
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
			return getBadRequestResponseEntity(errors);
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
			return getBadRequestResponseEntity(errors);
		}
		
		// 액션 별 간편 코멘트 입력
		if (!StringUtils.isNullOrEmpty(issue.getComment())) {
			Comment comment = new Comment();
			comment.setIssueId(id);
			comment.setComment(issue.getComment());
			commentService.createComment(comment);
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
	 * 코멘트 한개
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{issueId}/comments/{id}")
	public ResponseEntity<?> getComment(@PathVariable(value = "issueId") String issueId, @PathVariable(value = "id") String id) {
		validateIssue(issueId);
		
		return ResponseEntity.ok(commentService.getComment(issueId, id).orElseThrow(
				() -> new CommentNotFoundException(id)));
	}
	
	/**
	 * 코멘트 리스트
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{issueId}/comments")
	public ResponseEntity<?> getCommentList(@PathVariable(value = "issueId") String issueId) {
		validateIssue(issueId);
		
		return ResponseEntity.ok(commentService.getCommentList(issueId));
	}
	
	/**
	 * 코멘트 생성
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/{issueId}/comments")
	public ResponseEntity<?> createComment(@PathVariable(value = "issueId") String issueId,
			@Valid @RequestBody Comment comment, Errors errors) {
		validateIssue(issueId);
		
		// 필수값 validation error 메세지 처리
		if (errors.hasErrors()) {
			return getBadRequestResponseEntity(errors);
		}
		
		comment.setIssueId(issueId);
		Comment createComment = commentService.createComment(comment);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(createComment.getId()).toUri();
		
		return ResponseEntity.created(location).body(createComment);
	}

	
	/**
	 * 코멘트 수정
	 */
	@RequestMapping(method = RequestMethod.PATCH, value = "/{issueId}/comments/{id}")
	public ResponseEntity<?> updateComment(@PathVariable(value = "issueId") String issueId, @PathVariable(value = "id") String id,
			@Valid @RequestBody Comment comment, Errors errors) {
		validateIssue(issueId);
		validateComment(issueId, id);
		
		// 필수값 validation error 메세지 처리
		if (errors.hasErrors()) {
			return getBadRequestResponseEntity(errors);
		}
		
		comment.setId(id);
		comment.setIssueId(issueId);
		return ResponseEntity.ok(commentService.updateComment(comment));
	}
	
	/**
	 * 코멘트 삭제
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{issueId}/comments/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "issueId") String issueId, @PathVariable(value = "id") String id) {
		validateIssue(issueId);
		validateComment(issueId, id);
		
		commentService.deleteComment(issueId, id);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/**
	 * 이슈를 찾을 수 없을때 404 리턴
	 */
	private void validateIssue(String id) {
		issueService.getIssue(id).orElseThrow(
			() -> new IssueNotFoundException(id));
	}
	
	/**
	 * 코멘트를 찾을 수 없을때 404 리턴
	 */
	private void validateComment(String issueId, String id) {
		commentService.getComment(issueId, id).orElseThrow(
				() -> new CommentNotFoundException(id));
	}
	
	private ResponseEntity<?> getBadRequestResponseEntity(Errors errors) {
		String msg = 
				errors.getAllErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.collect(Collectors.joining(","));
		
		return ResponseEntity.badRequest().body(msg);
	}
}
