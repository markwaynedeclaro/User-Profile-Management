package com.mycompany.usermanagement.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="user_profile")
public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1071695303622286979L;
	
	@JsonIgnore
    @Column(name = "id", length = 20)
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;
    
	@Id
	@Column(name="username", length = 30)
	private String username;
	
	@Column(name="first_name", length = 50)
	private String firstName;
	
	@Column(name="last_name", length = 50)
	private String lastName;
		
	@Column(name="email", length = 50)
	private String email;
	
	@Column(name="birth_date", length = 10)
	private String birthDate;
	
    
	@OneToMany(targetEntity = ProfileAddress.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "username", referencedColumnName = "username")
	List<ProfileAddress> addresses;

	
	public UserProfile() {}

	public UserProfile(String username, String firstName) {
		super();
		this.username = username;
		this.firstName = firstName;
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


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}


	public List<ProfileAddress> getAddresses() {
		return addresses;
	}


	public void setAddresses(List<ProfileAddress> addresses) {
		this.addresses = addresses;
	}
	
}
