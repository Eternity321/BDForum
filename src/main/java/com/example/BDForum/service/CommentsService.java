package com.example.BDForum.service;

import com.example.BDForum.model.Comments;
import com.example.BDForum.model.Post;
import com.example.BDForum.repository.CommenstsRepository;
import com.example.BDForum.repository.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {

    @Autowired
    private CommenstsRepository commentsRepository;
    @Autowired
    private PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }

    public Optional<Comments> getCommentsById(Long commentsId) {
        return commentsRepository.findById(commentsId);
    }

    @Transactional
    public void addComment(Long postId, String text) {
        Optional<Post> postOptional = postService.getPostById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            Comments comment = new Comments();
            comment.setPost(post);
            comment.setText(text);
            comment.setTimestamp(LocalDateTime.now());
            commentsRepository.save(comment);
        } else {
            throw new PostNotFoundException("Пост с идентификатором " + postId + " не найден");
        }
    }

    public void updateComment(Long commentsId, Comments updatedComments) {
        Optional<Comments> existingComments = commentsRepository.findById(commentsId);
        if (existingComments.isPresent()) {
            Comments comments = existingComments.get();
            comments.setText(updatedComments.getText());
            comments.setTimestamp(updatedComments.getTimestamp());
            commentsRepository.save(comments);
        }
    }

    @Transactional
    public void deleteComment(Long commentsId) {
        try {
            Optional<Comments> commentOptional = commentsRepository.findById(commentsId);
            if(commentOptional.isPresent()){
                Comments comments = commentOptional.get();
                commentsRepository.deleteById(commentsId);
            } else {
                throw new PostNotFoundException("комментарий с идентификатором " + commentsId + " не найден");
            }
        } catch (DataAccessException e) {
            logger.error("Ошибка при удалении комментария с идентификатором: {}", commentsId, e);
            throw new RuntimeException("Ошибка при удалении поста", e);
        }
        commentsRepository.deleteById(commentsId);
    }
}
