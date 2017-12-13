package com.john.cena.service;

import java.util.List;
import java.util.Optional;

import com.john.cena.model.Comment;

public interface CommentService {
	public List<Comment> getCommentList(String issueId);
	public Optional<Comment> getComment(String issueId, String id);
	public Comment createComment(Comment comment);
	public Comment updateComment(Comment comment);
	public void deleteComment(String issueId, String id);
}
