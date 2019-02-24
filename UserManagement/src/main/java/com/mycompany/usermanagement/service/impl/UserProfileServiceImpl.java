package com.mycompany.usermanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.usermanagement.entities.ProfileAddress;
import com.mycompany.usermanagement.entities.UserProfile;
import com.mycompany.usermanagement.exception.AlreadyExistException;
import com.mycompany.usermanagement.exception.NotFoundException;
import com.mycompany.usermanagement.repository.ProfileAddressRepository;
import com.mycompany.usermanagement.repository.UserProfileRepository;
import com.mycompany.usermanagement.service.UserProfileService;



@Service
public class UserProfileServiceImpl implements UserProfileService  {

	@Autowired
	UserProfileRepository userProfileRepository;
	
	@Autowired
	ProfileAddressRepository profileAddressRepository;

	

	@Override
	public List<UserProfile> getAllUserProfiles() {
		return userProfileRepository.findAll();
	}
	
	@Override
	public UserProfile getUserProfile(String username) {
		return userProfileRepository.findByUsername(username)
				.orElseThrow(() -> new NotFoundException("Profile with username " +
						username + " was not found"));
	}

	@Override
	public void insertUserProfile(UserProfile userProfile) {
		
		if (userProfileRepository.findByUsername(userProfile.getUsername()).isPresent())
			throw new AlreadyExistException("Profile with username " + 
				userProfile.getUsername() + " already exist");
		
		for(ProfileAddress a : userProfile.getAddresses()) {
			a.setUsername(userProfile.getUsername());
		}
		userProfileRepository.save(userProfile);
	}
	
	@Override
	public void updateUserProfile(UserProfile userProfile) {

		if (!userProfileRepository.findByUsername(userProfile.getUsername()).isPresent())
			throw new NotFoundException("Profile with username " + 
				userProfile.getUsername() + " was not found");		
		
		profileAddressRepository.deleteByUsername(userProfile.getUsername());
		UserProfile updatedUserProfile = userProfileRepository.findByUsername(userProfile.getUsername()).get();
		updatedUserProfile.setEmail(userProfile.getEmail());
		updatedUserProfile.setFirstName(userProfile.getFirstName());
		updatedUserProfile.setLastName(userProfile.getLastName());
		updatedUserProfile.setBirthDate(userProfile.getBirthDate());
		updatedUserProfile.setAddresses(userProfile.getAddresses());
		for(ProfileAddress a : updatedUserProfile.getAddresses()) {
			a.setUsername(updatedUserProfile.getUsername());
		}

		userProfileRepository.saveAndFlush(updatedUserProfile);
	}

	@Override
	public void deleteUserProfile(String username) {
		
		if (!userProfileRepository.findByUsername(username).isPresent())
			throw new NotFoundException("Profile with username " + 
					username + " was not found");		
				
		userProfileRepository.deleteByUsername(username);
	}



}
