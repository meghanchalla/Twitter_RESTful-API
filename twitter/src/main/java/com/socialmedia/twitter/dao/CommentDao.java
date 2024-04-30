package com.socialmedia.twitter.dao;

import com.socialmedia.twitter.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDao extends JpaRepository<Comment, Integer> {
}

