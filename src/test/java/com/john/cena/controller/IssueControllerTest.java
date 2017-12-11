package com.john.cena.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.john.cena.model.Issue;
import com.john.cena.service.IssueService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class IssueControllerTest {
	private MockMvc mockMvc;

    @Autowired
    private IssueController issueController;

    @Autowired
    private IssueService issueService;
    
    @Before
    public void setUp() throws Exception {
        mockMvc = standaloneSetup(issueController).build();
    }
    
    private String jsonStringFromObject(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    /** 통합테스트 */
    @Test
    public void testGetIssueList() throws Exception {
    	Issue param = new Issue();
    	param.setTitle("test");
        List<Issue> issues = issueService.getIssueList(param);
        String jsonString = this.jsonStringFromObject(issues);

        mockMvc.perform(get("/api/issues?title=test"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(equalTo(jsonString)));
        
        param = new Issue();
    	param.setTitle("test1");
        issues = issueService.getIssueList(param);
        jsonString = this.jsonStringFromObject(issues);

        mockMvc.perform(get("/api/issues?title=test1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(equalTo(jsonString)));
    }
    
    @Test
    public void testGetIssue() throws Exception {
    	String id = "1";
    	Issue issue = issueService.getIssue(id).get();
        String jsonString = this.jsonStringFromObject(issue);

        mockMvc.perform(get("/api/issues/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(equalTo(jsonString)));
        
        // 예외처리 테스트
        mockMvc.perform(get("/api/issues/1111"))
        .andExpect(status().isNotFound());
    }
    
    @Test
    public void testCreateIssue() throws Exception {
    	Issue issue = new Issue();
    	issue.setTitle("test5");
    	issue.setDescription("test555");
    	issue.setPriority("A");
    	issue.setAssignee("1");
    	String paramJson = this.jsonStringFromObject(issue);
    	
    	mockMvc.perform(post("/api/issues/")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(paramJson))
        .andExpect(status().isCreated());
    	
    	Issue param = new Issue();
    	List<Issue> issueList = issueService.getIssueList(param);
    	assertThat(issueList.size(), equalTo(5));
    	
    	issueList = issueService.getIssueList(issue);
    	assertThat(issueList.size(), equalTo(1));
    	assertThat(issueList.get(0).getAssignee(), equalTo(issue.getAssignee()));
    	assertThat(issueList.get(0).getStatus(), equalTo("OPEN"));
    	assertThat(issueList.get(0).getId(), equalTo("5"));
    	assertThat(issueList.get(0).getPriority(), equalTo(issue.getPriority()));
    	
    	// 예외처리 테스트
    	issue = new Issue();
    	issue.setDescription("test555");
    	issue.setPriority("A");
    	issue.setAssignee("1");
    	paramJson = this.jsonStringFromObject(issue);
    	mockMvc.perform(post("/api/issues")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(paramJson))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(equalTo("title can't empty!")));
    }
    
    @Test
    public void testUpdateIssue() throws Exception {
    	Issue issue = new Issue();
    	issue.setId("1");
    	issue.setTitle("test5");
    	issue.setDescription("bbbbbbbbbbbbbbb");
    	issue.setPriority("D");
    	issue.setAssignee("ddddddddddddd");
    	String paramJson = this.jsonStringFromObject(issue);
    	
    	mockMvc.perform(patch("/api/issues/" + issue.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(paramJson))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testDeleteIssue() throws Exception {
    	Issue issue = new Issue();
    	issue.setId("1");
    	
    	String paramJson = this.jsonStringFromObject(issue);
    	
    	mockMvc.perform(delete("/api/issues/" + issue.getId())
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(paramJson))
        .andExpect(status().isOk());
    }
}
