package com.mycompany.usermanagement.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "profile_address")
public class ProfileAddress implements Serializable {

	private static final long serialVersionUID = -8392198284016272888L;

	@Id
	@JsonIgnore
	@Column(name = "address_id", length = 20)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long addressId;

	@JsonIgnore
	@Column(name = "username", length = 30)
	private String username;

	@Column(name = "address_type", length = 30)
	private String addressType;

	@Column(name = "line_1", length = 100)
	private String line1;

	@Column(name = "line_2", length = 100)
	private String line2;

	@Column(name = "town", length = 100)
	private String town;

	@Column(name = "postal_code", length = 12)
	private String postalCode;

	@Column(name = "region", length = 100)
	private String region;

	@Column(name = "country", length = 100)
	private String country;

	
	
	public ProfileAddress() {}
	
	
	
	
	

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
