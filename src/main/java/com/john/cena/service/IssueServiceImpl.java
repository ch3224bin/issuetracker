package com.john.cena.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.john.cena.mapper.IssueMapper;
import com.john.cena.model.Issue;

@Service
public class IssueServiceImpl implements IssueService {
	
	private IssueMapper issueMapper;

	@Autowired
	public void setIssueMapper(IssueMapper issueMapper) {
		this.issueMapper = issueMapper;
	}

	/**
	 * 전체 이슈 리스트 얻기
	 */
	public List<Issue> getIssueList() {
		return issueMapper.selectIssueList(new Issue());
	}
	
	/**
	 * 이슈 리스트 얻기
	 * @param param 검색 조건 
	 */
	public List<Issue> getIssueList(Issue param) {
		return issueMapper.selectIssueList(param);
	}
	
	/**
	 * 이슈 하나 읽기
	 * @param id 이슈ID
	 */
	public Optional<Issue> getIssue(String id) {
		return Optional.ofNullable(issueMapper.selectIssue(id));
	}

	/**
	 * 이슈 생성
	 */
	public Issue createIssue(Issue issue) {
		Map<String, String> userInfo = getCurrentUserInfo();
		issue.setReporter(userInfo.get("id"));
		issue.setId(generateIssueId());
		issueMapper.createIssue(issue);
		return issue;
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getCurrentUserInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Map<String, String> userInfo = new HashMap<String, String>();
		OAuth2Authentication auth = (OAuth2Authentication)authentication;
		if (auth != null) {
			Map<String, Map<String, String>> map = (Map<String, Map<String, String>>)auth.getUserAuthentication().getDetails();
			if (map != null) {
				userInfo = map.get("response");
			}
		}
		return userInfo;
	}

	/**
	 * 이슈 업데이트
	 */
	public Issue updateIssue(Issue issue) {
		issueMapper.updateIssue(issue);
		return issue;
	}

	/**
	 * 이슈 삭제
	 */
	public void deleteIssue(String id) {
		issueMapper.deleteIssue(id);
	}
	
	public String generateIssueId() {
		Issue issue = issueMapper.selectMaxIssueId();
		return issue.getId();
	}
	
}
