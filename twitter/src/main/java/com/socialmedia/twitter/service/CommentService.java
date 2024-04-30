package com.socialmedia.twitter.service;

import com.socialmedia.twitter.dao.CommentDao;
import com.socialmedia.twitter.dao.PostDao;
import com.socialmedia.twitter.dao.UserDao;
import com.socialmedia.twitter.model.Comment;
import com.socialmedia.twitter.model.Post;
import com.socialmedia.twitter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    public String createNewComment(String commentBody, int postId, int userId) {
        // Check if the post exists
        Optional<Post> optionalPost = postDao.findById(postId);
        if (optionalPost.isEmpty()) {
            return "Post does not exist";
        }

        // Check if the user exists
        Optional<User> optionalUser = userDao.findById(userId);
        if (optionalUser.isEmpty()) {
            return "User does not exist";
        }

        // Create the comment
        Comment comment = new Comment();
        comment.setCommentBody(commentBody);
        comment.setCommentDate(new Date());
        comment.setPost(optionalPost.get());
        comment.setUser(optionalUser.get());


        commentDao.save(comment);

        return "Comment created successfully";
    }


    public Comment getComment(int commentID) {
        return commentDao.findById(commentID).orElse(null);
    }

    public String editComment(int commentID, String commentBody) {
        Optional<Comment> optionalComment = commentDao.findById(commentID);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setCommentBody(commentBody);
            commentDao.save(comment);
            return "Comment edited successfully";
        } else {
            return "Comment does not exist";
        }
    }

    public String deleteComment(int commentID) {
        Optional<Comment> optionalComment = commentDao.findById(commentID);
        if (optionalComment.isPresent()) {
            commentDao.deleteById(commentID);
            return "Comment deleted";
        } else {
            return "Comment does not exist";
        }
    }
}