package com.mycompany.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.usermanagement.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	 Optional<User> findByUsername(String username);

}