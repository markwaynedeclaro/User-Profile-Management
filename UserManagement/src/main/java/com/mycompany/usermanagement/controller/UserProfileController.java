package com.mycompany.usermanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.mycompany.usermanagement.entities.UserProfile;
import com.mycompany.usermanagement.service.UserProfileService;



@RestController
@RequestMapping(value = "/v1/profile", produces = { "application/json", "application/xml" })
public class UserProfileController {

	private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
	
	@Autowired
	private UserProfileService userProfileService;
	
         
    // Get all User Profiles
    @GetMapping
    public List<UserProfile> getAllUserProfiles() {
    	logger.debug("Inside getAllUserProfiles()");
        return userProfileService.getAllUserProfiles();
    }  
    
    // Get a Single User Profile
    @GetMapping("/{username}")
    public UserProfile getUserProfileByUsername(@PathVariable(value = "username") String username) {
    	logger.debug("Inside getUserProfileByUsername()");
        return userProfileService.getUserProfile(username);
    }   	
	
    // Insert a User Profile record 
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void insertUserProfile(@RequestBody UserProfile userProfile){
    	logger.debug("---call insert UserProfile ---");
    	userProfileService.insertUserProfile(userProfile);
    }
    
    // Update a User Profile record 
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserProfile(@RequestBody UserProfile userProfile){
    	logger.debug("---call update User Profile ---");
    	userProfileService.updateUserProfile(userProfile);
    }
    
    // Delete a Single User Profile
    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserProfileByUsername(@PathVariable("username") String username){
    	userProfileService.deleteUserProfile(username);
    }   
 	
}
