package com.mycompany.usermanagement.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User implements Serializable {

	private static final long serialVersionUID = -6678494188669592455L;

	@Id
	@Column(name="username", length = 30)
	private String username;
    
	@Column(name="password", length = 100)
    private String password;
    
	@OneToMany(targetEntity = UserRole.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "username", referencedColumnName = "username")
	List<UserRole> userRoles;
	
	@Column(name="active")
    private boolean active;

    
    public User() {}
    
    
    
    public User(String username, String password, List<UserRole> userRoles, boolean active) {
    	super();
    	this.username = username;
    	this.password = password;
    	this.userRoles = userRoles;
    	this.active = active;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
        
}
