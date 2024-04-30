package com.socialmedia.twitter.controller;

import com.socialmedia.twitter.model.Comment;
import com.socialmedia.twitter.model.Post;
import com.socialmedia.twitter.model.PostRequestDTO;
import com.socialmedia.twitter.model.User;
import com.socialmedia.twitter.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> createPost(@RequestBody PostRequestDTO requestDTO) {
        String response = postService.createNewPost(requestDTO);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.status(response.equals("User does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.CREATED)
                .body(jsonResponse);
    }

    @PatchMapping("/post")
    public ResponseEntity<Map<String, String>> editPost(@RequestBody Map<String, Object> requestBody) {
        int postId = (int) requestBody.get("postId");
        String postBody = (String) requestBody.get("postBody");

        String response = postService.editPost(postId, postBody);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.status(response.equals("Post does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                .body(jsonResponse);
    }

    @DeleteMapping("/post")
    public ResponseEntity<Map<String, String>> deletePost(@RequestParam("postId") int postId) {
        String response = postService.deletePost(postId);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.status(response.equals("Post does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                .body(jsonResponse);
    }

    @GetMapping("/post")
    public ResponseEntity<?> getPost(@RequestParam("postID") int postID) {
        Optional<Post> optionalPost = postService.getPostById(postID);
        if (optionalPost.isPresent()) {
            // Exclude password field from user
            User user = optionalPost.get().getUser();
            user.setPassword(null);

            // Create response object with required format
            Map<String, Object> response = new HashMap<>();
            response.put("postID", optionalPost.get().getPostId());
            response.put("postBody", optionalPost.get().getPostBody());
            response.put("date", optionalPost.get().getDate());

            List<Map<String, Object>> commentsList = new ArrayList<>();
            for (Comment comment : optionalPost.get().getComments()) {
                // Exclude password field from comment creator user
                User commentCreator = comment.getUser();
                commentCreator.setPassword(null);

                Map<String, Object> commentMap = new HashMap<>();
                commentMap.put("commentID", comment.getCommentId());
                commentMap.put("commentBody", comment.getCommentBody());
                commentMap.put("commentDate", comment.getCommentDate());
                Map<String, Object> commentCreatorMap = new HashMap<>();
                commentCreatorMap.put("userID", commentCreator.getUser_id());
                commentCreatorMap.put("name", commentCreator.getName());
                commentMap.put("commentCreator", commentCreatorMap);
                commentsList.add(commentMap);
            }
            response.put("comments", commentsList);

            return ResponseEntity.ok(response);
        } else {
            Map<String, String> jsonResponse = new HashMap<>();
            jsonResponse.put("error", "Post does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

}
