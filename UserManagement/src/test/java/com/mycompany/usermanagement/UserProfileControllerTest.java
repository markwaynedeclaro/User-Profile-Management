package com.mycompany.usermanagement;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;

import com.mycompany.usermanagement.entities.UserProfile;
import com.mycompany.usermanagement.service.UserProfileService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = UserManagementApplication.class)
public class UserProfileControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    private static final String CLIENT_ID = "mycompany-trusted-client";
    private static final String CLIENT_SECRET = "secret";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    
    private String ACCESS_TOKEN;
    
    
    
    
    
    @MockBean
    private UserProfileService userProfileService;
	
    
    
    
	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
		ACCESS_TOKEN = obtainAccessToken("user1", "password1");
	}

	private String obtainAccessToken(String username, String password) throws Exception {

		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "password");
		params.add("client_id", CLIENT_ID);
		params.add("username", username);
		params.add("password", password);

		ResultActions result = mockMvc
				.perform(post("/oauth/token").params(params).with(httpBasic(CLIENT_ID, CLIENT_SECRET))
						.accept(CONTENT_TYPE))
				.andExpect(status().isOk()).andExpect(content().contentType(CONTENT_TYPE));

		String resultString = result.andReturn().getResponse().getContentAsString();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}

	@Test
	public void testGetAllUserProfiles() throws Exception {

		UserProfile moks1 = new UserProfile("moks1", "Mark Wayne");
		UserProfile moks2 = new UserProfile("moks2", "Mark1 Wayne1");
	    List<UserProfile> AllUserProfiles = Arrays.asList(moks1, moks2);
	 
	    Mockito.when(userProfileService.getAllUserProfiles()).thenReturn(AllUserProfiles);	
	 
	    mockMvc
	    	.perform(get("/v1/profile")
   			.header("Authorization", "Bearer " + ACCESS_TOKEN)
	    	.contentType(MediaType.APPLICATION_JSON))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$", hasSize(2)))
	        .andExpect(jsonPath("$[0].firstName", is(moks1.getFirstName())));
	    
	}
	
	
	@Test
	public void testGetUserProfileByUsername() throws Exception {

		UserProfile moks1 = new UserProfile("moks1", "Mark Wayne");
		 
	    Mockito.when(userProfileService.getUserProfile("moks1")).thenReturn(moks1);	
	 
	    mockMvc.perform(get("/v1/profile/moks1")
               .header("Authorization", "Bearer " + ACCESS_TOKEN)
               .accept(CONTENT_TYPE))
               .andExpect(status().isOk())
               .andExpect(content().contentType(CONTENT_TYPE))
               .andExpect(jsonPath("$.firstName", is("Mark Wayne")));
	}
	
	
	
	@Test
	public void testInsertUserProfile() throws Exception {

		String userProfileString =  "{\r\n" + 
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
        		.header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(CONTENT_TYPE)
                .content(userProfileString)
                .accept(CONTENT_TYPE))
                .andExpect(status().isCreated());
	}
	
	
	@Test
	public void testUpdateUserProfile() throws Exception {

		String userProfileString =  "{\r\n" + 
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
        		.header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(CONTENT_TYPE)
                .content(userProfileString)
                .accept(CONTENT_TYPE))
                .andExpect(status().isCreated());
	    
	    
		String userProfileUpdateString =  "{\r\n" + 
				"    \"username\": \"mwdeclaro1\",\r\n" + 
				"    \"firstName\": \"Mark Wayne\",\r\n" + 
				"    \"lastName\": \"de Claro\",\r\n" + 
				"    \"email\": \"mwdeclaro1@mycompany.com\",\r\n" + 
				"    \"birthDate\": \"1900-01-01\",\r\n" + 
				"    \"addresses\": [\r\n" + 
				"        {\r\n" + 
				"            \"addressType\": \"office\",\r\n" + 
				"            \"line1\": \"345 George St.\",\r\n" + 
				"            \"line2\": \"\",\r\n" + 
				"            \"town\": \"Sydney\",\r\n" + 
				"            \"postalCode\": \"2000\",\r\n" + 
				"            \"region\": \"New South Wales\",\r\n" + 
				"            \"country\": \"Australia\"\r\n" + 
				"        }\r\n" + 
				"    ]\r\n" + 
				"}";	
		
				
	     mockMvc.perform(put("/v1/profile")
        		.header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(CONTENT_TYPE)
                .content(userProfileUpdateString)
                .accept(CONTENT_TYPE))
                .andExpect(status().isNoContent());
	}	
	
	
	@Test
	public void testDeleteUserProfile() throws Exception {

		String userProfileString =  "{\r\n" + 
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
        		.header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(CONTENT_TYPE)
                .content(userProfileString)
                .accept(CONTENT_TYPE))
                .andExpect(status().isCreated());
	    
	     mockMvc.perform(delete("/v1/profile/mwdeclaro1")
        		.header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(CONTENT_TYPE)
                .accept(CONTENT_TYPE))
                .andExpect(status().isNoContent());
	}		
}
