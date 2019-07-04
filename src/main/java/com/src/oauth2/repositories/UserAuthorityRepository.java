package com.src.oauth2.repositories;

import com.src.oauth2.domain.RegisteredUser;
import com.src.oauth2.domain.UserAuthority;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Priya on 19/3/17.
 */
@Repository
public interface UserAuthorityRepository extends CrudRepository<UserAuthority, Long> {
	Page<UserAuthority> findAll(Pageable pageable);

	List<UserAuthority> findByUser(RegisteredUser registeredUser);

	@Query(value = "select * from authorities where username = :username", nativeQuery = true)
	List<UserAuthority> findByUsername(@Param("username") String username);
}
