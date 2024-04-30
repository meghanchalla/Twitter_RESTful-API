package com.socialmedia.twitter.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostRequestDTO {
    private String postBody;
    private int userID;
}
