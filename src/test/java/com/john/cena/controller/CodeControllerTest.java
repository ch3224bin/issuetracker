package com.john.cena.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.john.cena.model.Code;
import com.john.cena.service.CodeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeControllerTest {
	private MockMvc mockMvc;

    @Autowired
    private CodeController codeController;
    
    @Autowired
    private CodeService codeService;
    
    @Before
    public void setUp() throws Exception {
        mockMvc = standaloneSetup(codeController).build();
    }
    
    private String jsonStringFromObject(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
    
    /** 통합테스트 */
    @Test
    public void testGetCodeList() throws Exception {
    	String codeGroup = "PRIORITY";
        List<Code> codeList = codeService.getCodeList(codeGroup);
        String jsonString = this.jsonStringFromObject(codeList);

        mockMvc.perform(get("/api/codegroups/" + codeGroup + "/codes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string(equalTo(jsonString)));
    }
}
