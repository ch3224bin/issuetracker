package com.john.cena.service;

import java.util.List;
import java.util.Optional;

import com.john.cena.model.Issue;

public interface IssueService {
	public List<Issue> getIssueList();
	public List<Issue> getIssueList(Issue param);
	public Optional<Issue> getIssue(String id);
	public Issue createIssue(Issue issue);
	public Issue updateIssue(Issue issue);
	public void deleteIssue(String id);
}
