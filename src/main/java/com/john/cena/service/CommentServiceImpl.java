package com.john.cena.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.john.cena.mapper.CommentMapper;
import com.john.cena.model.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	
	private CommentMapper commentMapper;
	private UserService userService;

	@Autowired
	public void setCommentMapper(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Comment> getCommentList(String issueId) {
		Comment param = new Comment();
		param.setId(issueId);
		return commentMapper.selectCommentList(param);
	}

	public Optional<Comment> getComment(String issueId, String id) {
		Comment param = new Comment();
		param.setId(id);
		param.setIssueId(issueId);
		return Optional.ofNullable(commentMapper.selectComment(param));
	}

	public Comment createComment(Comment comment) {
		Map<String, String> userInfo = userService.getCurrentUserInfo();
		comment.setCreateUser(userInfo.get("id"));
		comment.setId(generateCommentId());
		commentMapper.createComment(comment);
		return comment;
	}

	public Comment updateComment(Comment comment) {
		Map<String, String> userInfo = userService.getCurrentUserInfo();
		comment.setUpdateUser(userInfo.get("id"));
		commentMapper.updateComment(comment);
		return comment;
	}

	public void deleteComment(String issueId, String id) {
		Comment comment = new Comment();
		comment.setId(id);
		comment.setIssueId(issueId);
		commentMapper.deleteComment(comment);
	}
	
	public String generateCommentId() {
		Comment comment = commentMapper.generateCommentId();
		return comment.getId();
	}

}
