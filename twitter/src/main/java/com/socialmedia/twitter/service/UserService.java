package com.socialmedia.twitter.service;



import com.socialmedia.twitter.model.User;
import com.socialmedia.twitter.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    public List<User> getAllUsers() {
       return userDao.findAll();

    }

    public boolean isUserExists(String email) {
        return userDao.existsByEmail(email);
    }

    public void createUser(User user) {
        userDao.save(user);
    }

    public boolean authenticateUser(User user) {
        User existingUser = userDao.findByEmail(user.getEmail());
        return existingUser != null && existingUser.getPassword().equals(user.getPassword());
    }

    public User getUserById(int id) {
        return userDao.findById(id).orElse(null);
    }




}
