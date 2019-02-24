package com.mycompany.usermanagement;


import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;



@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = UserManagementApplication.class)
public class OAuthTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    private static final String CLIENT_ID = "mycompany-trusted-client";
    private static final String CLIENT_SECRET = "secret";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
    }

    private String obtainAccessToken(String username, String password) throws Exception {

    	System.out.println(">>>>>>>> "+username+", "+password);
    	
    	final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", CLIENT_ID);
        params.add("username", username);
        params.add("password", password);

        ResultActions result = mockMvc.perform(post("/oauth/token")
                               .params(params)
                               .with(httpBasic(CLIENT_ID, CLIENT_SECRET))
                               .accept(CONTENT_TYPE))
                               .andExpect(status().isOk())
                               .andExpect(content().contentType(CONTENT_TYPE));

        String resultString = result.andReturn().getResponse().getContentAsString();
        System.out.println(">>>> "+resultString);
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    public void noTokenGiven_whenGetSecureRequest_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/v1/profile"))
        	.andExpect(status().isUnauthorized());
    }
    
/*
    @Test
    public void givenInvalidRole_whenGetSecureRequest_thenForbidden() throws Exception {

    	final String accessToken = obtainAccessToken("user2", "password2");
        
    	System.out.println("token:" + accessToken);
        mockMvc.perform(get("/v1/profile")
        		.header("Authorization", "Bearer " + accessToken))
        		.andExpect(status().isForbidden());
    }

*/    
    
    @Test
    public void givenToken_whenGetGetSecureRequest_thenNotFound() throws Exception {

    	final String accessToken = obtainAccessToken("user1", "password1");
        
    	System.out.println("token:" + accessToken);
        mockMvc.perform(get("/v1/profilezzz")
        		.header("Authorization", "Bearer " + accessToken))
        		.andExpect(status().isNotFound());
    }
    
    @Test
    public void givenToken_whenPostGetSecureRequest_thenOk() throws Exception {

    	final String accessToken = obtainAccessToken("user1", "password1");

        String userProfileString = "{\r\n" + 
        		"    \"username\": \"mwdeclaro1\",\r\n" + 
        		"    \"firstName\": \"Mark Wayne\",\r\n" + 
        		"    \"lastName\": \"de Claro\",\r\n" + 
        		"    \"email\": \"mwdeclaro1@mycompany.com\",\r\n" + 
        		"    \"birthDate\": \"1900-01-01\",\r\n" + 
        		"    \"addresses\": [\r\n" + 
        		"        {\r\n" + 
        		"            \"addressType\": \"office\",\r\n" + 
        		"            \"line1\": \"167 Macquarie St.\",\r\n" + 
        		"            \"line2\": \"\",\r\n" + 
        		"            \"town\": \"Sydney\",\r\n" + 
        		"            \"postalCode\": \"2000\",\r\n" + 
        		"            \"region\": \"New South Wales\",\r\n" + 
        		"            \"country\": \"Australia\"\r\n" + 
        		"        }\r\n" + 
        		"    ]\r\n" + 
        		"}";
        
        mockMvc.perform(post("/v1/profile")
        		.header("Authorization", "Bearer " + accessToken)
                .contentType(CONTENT_TYPE)
                .content(userProfileString)
                .accept(CONTENT_TYPE))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/v1/profile/mwdeclaro1")
                .header("Authorization", "Bearer " + accessToken)
                .accept(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.firstName", is("Mark Wayne")));
       
    }

}