package com.john.cena.model;

import org.hibernate.validator.constraints.NotBlank;

public class Issue {
	private String id;
	@NotBlank(message = "title can't empty!")
	private String title;
	private String description;
	@NotBlank(message = "priority can't empty!")
	private String priority;
	private String priorityLabel;
	private String status;
	private String statusLabel;
	@NotBlank(message = "assignee can't empty!")
	private String assignee;
	private String assigneeLabel;
	private String createDate;
	private String updateDate;
	private String resolvedDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
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
	public String getResolvedDate() {
		return resolvedDate;
	}
	public void setResolvedDate(String resolvedDate) {
		this.resolvedDate = resolvedDate;
	}
	public String getPriorityLabel() {
		return priorityLabel;
	}
	public void setPriorityLabel(String priorityLabel) {
		this.priorityLabel = priorityLabel;
	}
	public String getStatusLabel() {
		return statusLabel;
	}
	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}
	public String getAssigneeLabel() {
		return assigneeLabel;
	}
	public void setAssigneeLabel(String assigneeLabel) {
		this.assigneeLabel = assigneeLabel;
	}
}
