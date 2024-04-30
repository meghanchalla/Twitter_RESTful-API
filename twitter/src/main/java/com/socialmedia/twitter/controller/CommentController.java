package com.socialmedia.twitter.controller;

import com.socialmedia.twitter.model.Comment;
import com.socialmedia.twitter.model.CommentRequestDTO;
import com.socialmedia.twitter.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<Map<String, String>> createComment(@RequestBody CommentRequestDTO commentRequestDTO) {
        String commentBody = commentRequestDTO.getCommentBody();
        int postID = commentRequestDTO.getPostID();
        int userID = commentRequestDTO.getUserID();

        String response = commentService.createNewComment(commentBody, postID, userID);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.status(response.equals("User does not exist") || response.equals("Post does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.CREATED)
                .body(jsonResponse);
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getComment(@RequestParam("commentID") int commentID) {
        Comment comment = commentService.getComment(commentID);
        if (comment == null) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Comment does not exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("commentID", comment.getCommentId());
            response.put("commentBody", comment.getCommentBody());

            Map<String, Object> commentCreator = new HashMap<>();
            commentCreator.put("userID", comment.getUser().getUser_id());
            commentCreator.put("name", comment.getUser().getName());

            response.put("commentCreator", commentCreator);

            return ResponseEntity.ok(response);
        }
    }

    @PatchMapping("/comment")
    public ResponseEntity<Map<String, String>> editComment(@RequestBody Map<String, Object> requestBody) {
        int commentID = (int) requestBody.get("commentID");
        String commentBody = (String) requestBody.get("commentBody");

        String response = commentService.editComment(commentID, commentBody);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.status(response.equals("Comment does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                .body(jsonResponse);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<Map<String, String>> deleteComment(@RequestParam("commentID") int commentID) {
        String response = commentService.deleteComment(commentID);
        Map<String, String> jsonResponse = new HashMap<>();
        jsonResponse.put("message", response);
        return ResponseEntity.status(response.equals("Comment does not exist") ? HttpStatus.NOT_FOUND : HttpStatus.OK)
                .body(jsonResponse);
    }
}
