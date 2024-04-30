package com.socialmedia.twitter.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Users-Info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int user_id;


    private String name;
    private String email;
    private String password;


}
