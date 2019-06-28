package com.src.oauth2.domain;

import com.src.oauth2.domain.entity.CommonDataEntity;
import com.src.oauth2.enums.LoginSignupType;
import com.src.oauth2.utils.DateTimeUtils;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Priya on 11/3/17.
 */
@Entity
@Table(name = "user_registration")
@Data
public class RegisteredUser extends CommonDataEntity {
	private String firstName;
	private String lastName;
	@Column(nullable = false, unique = true, name = "username")
	private String email;
	@Column(nullable = false, unique = true, name = "referral_code")
	private String referralCode;
	private String photoUrl;
	@Column(nullable = true)// todo ... temporarity setting the property to nullable, as fb doesnt give phone number
	private String phoneNumber;
	private String upiId;
	private Integer pointsBalance;
	private String password;

	@Column(name = "social_id")
	@Enumerated(EnumType.STRING)
	private LoginSignupType loginSignupType;

	private String userSocialId;
	@Column(columnDefinition = "boolean default false")
	private boolean enabled;
	@Column(columnDefinition = "boolean default true")
	private boolean subscribedToEmail;

	public RegisteredUser() {
		this.setDateCreated(DateTimeUtils.getCurrentDate());
	}
}
