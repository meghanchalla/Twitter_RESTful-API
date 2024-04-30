package com.socialmedia.twitter.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    private String commentBody;
    private int postID;
    private int userID;


}

