package com.john.cena.model;

import org.hibernate.validator.constraints.NotBlank;

public class Comment {
	private String id;
	private String issueId;
	@NotBlank(message = "comment can't empty!")
	private String comment;
	private String createUser;
	private String createUserLabel;
	private String updateUser;
	private String createDate;
	private String updateDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreateUserLabel() {
		return createUserLabel;
	}
	public void setCreateUserLabel(String createUserLabel) {
		this.createUserLabel = createUserLabel;
	}
}
