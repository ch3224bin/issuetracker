package com.john.cena.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.john.cena.model.Issue;

@Mapper
public interface IssueMapper {
	public List<Issue> selectIssueList(Issue param);
	public Issue selectIssue(String id);
	public void createIssue(Issue issue);
	public void updateIssue(Issue issue);
	public void deleteIssue(String id);
	public Issue selectMaxIssueId();
}
