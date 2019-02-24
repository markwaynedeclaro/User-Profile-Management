package com.mycompany.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.usermanagement.entities.ProfileAddress;

@Repository
public interface ProfileAddressRepository extends JpaRepository<ProfileAddress, Long> {
	
	@Transactional
    @Modifying
	void deleteByUsername(String username);	
	
}
