package com.src.oauth2.dao.interfaces;

import com.src.oauth2.domain.RegisteredUser;
import com.src.oauth2.domain.UserAuthority;
import com.src.oauth2.searchfilter.UserAuthoritySearchFilter;

import java.util.List;

/**
 * Created by Priya on 19/3/17.
 */
public interface UserAuthorityDao extends BaseDao<UserAuthority, UserAuthoritySearchFilter> {
    List<UserAuthority> getUserAuthorities(RegisteredUser registeredUser);
}
