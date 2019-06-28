package com.src.oauth2.dao.impl;

import com.src.oauth2.dao.interfaces.RegisteredUserDao;
import com.src.oauth2.domain.RegisteredUser;
import com.src.oauth2.searchfilter.RegisteredUserSearchFilter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * oauth2
 * Mrityunjaya@infoedge
 * 27/6/19 4:19 PM
 */
@Component
public class RegisteredUserDaoImpl implements RegisteredUserDao {
	/**
	 * email and username are the same
	 *
	 * @param username
	 * @return
	 */
	@Override
	public RegisteredUser getByUsername(String username) {
		return null;
	}

	@Override
	public List<RegisteredUser> findAllUsersByEnabled() {
		return null;
	}

	@Override
	public RegisteredUser getUserByPromoCode(String promoCode) {
		return null;
	}

	@Override
	public List<RegisteredUser> getByFirstName(String firstName) {
		return null;
	}

	@Override
	public List<RegisteredUser> getByReferralCode(String refCode) {
		return null;
	}

	@Override
	public Set<RegisteredUser> getByEmailLike(String passedEmail) {
		return null;
	}

	@Override
	public Set<RegisteredUser> getByEmailLikeAndCreatedBy(String s, Long id) {
		return null;
	}

	@Override
	public List<RegisteredUser> findItems(RegisteredUserSearchFilter registeredUserSearchFilter) {
		return null;
	}

	@Override
	public void save(RegisteredUser registeredUser) {

	}

	@Override
	public void update(RegisteredUser registeredUser) {

	}

	@Override
	public RegisteredUser get(Long id) {
		return null;
	}

	@Override
	public List<RegisteredUser> findAll() {
		return null;
	}

	@Override
	public long count() {
		return 0;
	}
}
