package com.mycompany.usermanagement.service;

import java.util.List;

import com.mycompany.usermanagement.entities.UserProfile;

public interface UserProfileService {
	
	List<UserProfile> getAllUserProfiles();
	
	UserProfile getUserProfile(String username);
	
	void insertUserProfile(UserProfile userProfile);
	
	void updateUserProfile(UserProfile userProfile);
	
	void deleteUserProfile(String username);

}
