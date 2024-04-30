package com.socialmedia.twitter.service;

import com.socialmedia.twitter.dao.PostDao;
import com.socialmedia.twitter.dao.UserDao;
import com.socialmedia.twitter.model.Post;
import com.socialmedia.twitter.model.PostRequestDTO;
import com.socialmedia.twitter.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    public String createNewPost(PostRequestDTO requestDTO) {
        // Check if the user exists
        Optional<User> optionalUser = userDao.findById(requestDTO.getUserID());
        if (optionalUser.isEmpty()) {
            return "User does not exist";
        }

        // Create a new post
        Post post = new Post();
        post.setPostBody(requestDTO.getPostBody());
        post.setUser(optionalUser.get());
        postDao.save(post);

        return "Post created successfully";
    }


    public String editPost(int postId, String postBody) {
        // Check if the post exists
        Optional<Post> optionalPost = postDao.findById(postId);
        if (optionalPost.isEmpty()) {
            return "Post does not exist";
        }

        // Update the post body
        Post post = optionalPost.get();
        post.setPostBody(postBody);

        // Save the updated post
        postDao.save(post);

        return "Post edited successfully";
    }


    public String deletePost(int postId) {
        // Check if the post exists
        Optional<Post> optionalPost = postDao.findById(postId);
        if (optionalPost.isEmpty()) {
            return "Post does not exist";
        }

        // Delete the post
        postDao.deleteById(postId);

        return "Post deleted";
    }

    public Optional<Post> getPostById(int postId) {
        return postDao.findById(postId);
    }

    public List<Post> getAllPosts() {
        // Fetch all posts sorted by date in descending order
        return postDao.findAllByOrderByDateDesc();
    }
}

