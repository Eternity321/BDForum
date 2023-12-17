package com.example.BDForum.service;

import com.example.BDForum.model.User;
import com.example.BDForum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private final CommentsService commentsService;

    public UserService(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void updateUser(Long userId, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setMail(updatedUser.getMail());
            user.setPassword(updatedUser.getPassword());
            user.setUsername(updatedUser.getUsername());
            userRepository.save(user);
        }
    }

    public void deleteUser(Long userId) {
        commentsService.deleteComment(userId);
        userRepository.deleteById(userId);
    }
}
