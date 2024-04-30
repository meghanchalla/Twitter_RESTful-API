package com.socialmedia.twitter.dao;

import com.socialmedia.twitter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
