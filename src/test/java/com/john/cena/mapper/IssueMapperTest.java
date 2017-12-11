package com.john.cena.mapper;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.john.cena.model.Issue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IssueMapperTest {
	@Autowired
	IssueMapper issueMapper;

	@Test
	public void testSelectIssueList() {
		Issue param = new Issue();
		List<Issue> list = issueMapper.selectIssueList(param);
		assertThat(list.size(), equalTo(4));
		
		param = new Issue();
		param.setPriority("A");
		list = issueMapper.selectIssueList(param);
		assertThat(list.size(), equalTo(2));
		
		param = new Issue();
		param.setAssignee("1");
		list = issueMapper.selectIssueList(param);
		assertThat(list.size(), equalTo(2));
		
		param = new Issue();
		param.setAssignee("제프");
		list = issueMapper.selectIssueList(param);
		assertThat(list.size(), equalTo(2));
	}
	
	@Test
	public void testSelectIssue() {
		Issue issue = issueMapper.selectIssue("1");
		assertThat(issue.getId(), equalTo("1"));
		assertThat(issue.getTitle(), equalTo("test1"));
		assertThat(issue.getDescription(), equalTo("<script>alert(1);</script>"));
		assertThat(issue.getPriorityLabel(), equalTo("Critical"));
		assertThat(issue.getStatusLabel(), equalTo("Open"));
		assertThat(issue.getPriority(), equalTo("A"));
		assertThat(issue.getStatus(), equalTo("OPEN"));
		assertThat(issue.getCreateDate(), equalTo(toDay()));
	}
	
	private String toDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
}
