package com.john.cena.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.john.cena.model.Comment;

@Mapper
public interface CommentMapper {
	public List<Comment> selectCommentList(Comment param);
	public Comment selectComment(Comment param);
	public void createComment(Comment comment);
	public void updateComment(Comment comment);
	public void deleteComment(Comment comment);
	public Comment generateCommentId();
}
