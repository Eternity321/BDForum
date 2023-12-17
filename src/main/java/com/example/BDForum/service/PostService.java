package com.example.BDForum.service;

import com.example.BDForum.model.Post;
import com.example.BDForum.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public void createPost(Post post) {
        post.setTimestamp(LocalDateTime.now());
        postRepository.save(post);
    }

    public void updatePost(Long postId, Post updatedPost) {
        Optional<Post> existingPost = postRepository.findById(postId);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setText(updatedPost.getText());
            post.setTimestamp(LocalDateTime.now());
            postRepository.save(post);
        }
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
