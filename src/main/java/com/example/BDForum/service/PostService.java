package com.example.BDForum.service;

import com.example.BDForum.model.Comments;
import com.example.BDForum.model.Post;
import com.example.BDForum.repository.PostNotFoundException;
import com.example.BDForum.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.BDForum.repository.CommenstsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommenstsRepository commentRepository;

    public List<Post> getAllPosts(Sort sort) {
        try {
            return postRepository.findAll(sort);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении постов", e);
        }
    }

    public long getTotalPosts() {
        try {
            return postRepository.count();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении общего количества постов", e);
        }
    }

    public List<Post> getAllPostsByUsername(String username, Sort sort) {
        try {
            return postRepository.findByUserUsername(username, sort);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении постов по имени пользователя", e);
        }
    }



    public Optional<Post> getPostById(Long postId) {
        try {
            return postRepository.findById(postId);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении поста по идентификатору: " + postId, e);
        }
    }


    public void createPost(Post post) {
        try {
            post.setTimestamp(LocalDateTime.now());
            postRepository.save(post);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при создании поста", e);
        }
    }

    public void updatePost(Long postId, Post updatedPost) {
        try {
            postRepository.findById(postId).ifPresent(post -> {
                post.setTitle(updatedPost.getTitle());
                post.setText(updatedPost.getText());
                post.setTimestamp(LocalDateTime.now());
                postRepository.save(post);
            });
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при обновлении поста", e);
        }
    }
    @Transactional
    public void deletePost(Long postId) {
        try {
            Optional<Post> postOptional = postRepository.findById(postId);
            if (postOptional.isPresent()) {
                Post post = postOptional.get();
                commentRepository.deleteByPost(post);
                postRepository.deleteById(postId);
            } else {
                throw new PostNotFoundException("Пост с идентификатором " + postId + " не найден");
            }
        } catch (DataAccessException e) {
            logger.error("Ошибка при удалении поста с идентификатором: {}", postId, e);
            throw new RuntimeException("Ошибка при удалении поста", e);
        }
    }

}
