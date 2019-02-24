package com.mycompany.usermanagement.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_role")
public class UserRole implements Serializable {

	private static final long serialVersionUID = 8569698166673608844L;

    @Column(name = "id", length = 20)
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;
    
	@Id
	@Column(name="username", length = 30)
	private String username;
	
	@Column(name="role", length = 50)
	private String role;	
	
	
	
	public UserRole() {}

	public UserRole(String username, String role) {
		super();
		this.username = username;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
