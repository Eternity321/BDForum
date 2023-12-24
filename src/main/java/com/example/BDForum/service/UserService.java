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
        try {
            return userRepository.findAll();
        } catch (Exception e){
            throw new RuntimeException("Ошибка при получении пользователей.", e);
        }
    }

    public Optional<User> getUserById(Long userId) {
        try {
            return userRepository.findById(userId);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении пользователя по идентификатору: " + userId, e);
        }
    }

    public User addUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка добавления пользователя", e);
        }
    }

    public void updateUser(Long userId, User updatedUser) {
        try {
            userRepository.findById(userId).ifPresent(user -> {
                user.setMail(updatedUser.getMail());
                user.setPassword(updatedUser.getPassword());
                user.setUsername(updatedUser.getUsername());
                userRepository.save(user);
            });
        } catch (Exception e) {

            throw new RuntimeException("Ошибка обновления пользователя", e);
        }
    }

    public void deleteUser(Long userId) {
        try {
            commentsService.deleteComment(userId);
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка удаления пользователя", e);
        }
    }
}
