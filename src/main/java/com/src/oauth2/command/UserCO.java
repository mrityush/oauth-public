package com.src.oauth2.command;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserCO implements Serializable {
	@Id
	private String uId;
	private Long id;
	private String lastName;
	private String country;
	private String address;
	private String city;
	private String firstName;
	private String password;
	private String pin;
	private String phone;
	private String district;
	private String company;
	private String state;
	private String category;
	private String email;
	private String username;
	private Integer language;
	private Integer userRole;
	private Date timestamp;
}
