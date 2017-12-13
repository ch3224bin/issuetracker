package com.john.cena.model;

import java.util.List;

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
	private String reporter;
	private String reporterLabel;
	private String resolver;
	private String resolverLabel;
	private String createDate;
	private String updateDate;
	private String resolvedDate;
	private List<Comment> comments;
	
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
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
	public String getReporterLabel() {
		return reporterLabel;
	}
	public void setReporterLabel(String reporterLabel) {
		this.reporterLabel = reporterLabel;
	}
	public String getResolver() {
		return resolver;
	}
	public void setResolver(String resolver) {
		this.resolver = resolver;
	}
	public String getResolverLabel() {
		return resolverLabel;
	}
	public void setResolverLabel(String resolverLabel) {
		this.resolverLabel = resolverLabel;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
