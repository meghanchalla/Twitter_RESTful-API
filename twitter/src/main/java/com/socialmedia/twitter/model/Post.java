package com.socialmedia.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;

    @Column(name = "post_body")
    private String postBody;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date")
    private Date date = new Date(); // Initialize with the current date

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<Comment> comments;
}
