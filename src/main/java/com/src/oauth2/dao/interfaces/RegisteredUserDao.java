package com.src.oauth2.dao.interfaces;


import com.src.oauth2.domain.RegisteredUser;
import com.src.oauth2.searchfilter.RegisteredUserSearchFilter;

import java.util.List;
import java.util.Set;

/**
 * Created by Priya on 11/3/2017
 */
public interface RegisteredUserDao extends BaseDao<RegisteredUser, RegisteredUserSearchFilter> {
	/**
	 * email and username are the same
	 *
	 * @param username
	 * @return
	 */
	RegisteredUser getByUsername(String username);

	List<RegisteredUser> findAllUsersByEnabled();

	RegisteredUser getUserByPromoCode(String promoCode);

	List<RegisteredUser> getByFirstName(String firstName);

	List<RegisteredUser> getByReferralCode(String refCode);

	Set<RegisteredUser> getByEmailLike(String passedEmail);

	Set<RegisteredUser> getByEmailLikeAndCreatedBy(String s, Long id);
}
