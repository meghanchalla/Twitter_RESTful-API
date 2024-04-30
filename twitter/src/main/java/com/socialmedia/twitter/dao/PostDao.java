package com.socialmedia.twitter.dao;

import com.socialmedia.twitter.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDao extends JpaRepository<Post, Integer> {
    List<Post> findAllByOrderByDateDesc();
}
