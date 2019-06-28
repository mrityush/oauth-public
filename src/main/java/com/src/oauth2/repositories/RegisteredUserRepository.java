package com.src.oauth2.repositories;

import com.src.oauth2.domain.RegisteredUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Long> {
    @Query(value = "select * from user_registration ur where ur.username  = :username", nativeQuery = true)
    List<RegisteredUser> findByEmail(@Param("username") String username);

    Page<RegisteredUser> findAll(Pageable pageable);

    @Query(value = "select * from user_registration ur inner join authorities au on au.username=ur.username where au.authority='ROLE_USER' and ur.enabled=1", nativeQuery = true)
    List<RegisteredUser> findAllUsersByEnabled();

    @Query(value = "select * from user_registration ur where ur.referral_code = :refer_code and ur.enabled=1", nativeQuery = true)
    RegisteredUser findUserByPromocode(@Param("refer_code") String referCode);

    List<RegisteredUser> findByFirstName(String firstName);

    List<RegisteredUser> findByReferralCode(String refCode);

    @Query(value = "select * from user_registration ur where ur.username like :passedEmail", nativeQuery = true)
    Set<RegisteredUser> findByEmailLike(@Param("passedEmail") String passedEmail);

    @Query(value = "select * from user_registration ur where ur.username like :passedEmail and created_by = :created_by", nativeQuery = true)
    Set<RegisteredUser> getByEmailLikeAndCreatedBy(@Param("passedEmail") String passedEmail, @Param("created_by") Long id);
}
