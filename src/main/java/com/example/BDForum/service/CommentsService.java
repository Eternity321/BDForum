package com.example.BDForum.service;

import com.example.BDForum.model.Comments;
import com.example.BDForum.repository.CommenstsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {

    @Autowired
    private CommenstsRepository commentsRepository;

    public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }

    public Optional<Comments> getCommentsById(Long commentsId) {
        return commentsRepository.findById(commentsId);
    }

    public void createComment(Comments comments) {
        commentsRepository.save(comments);
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

    public void deleteComment(Long commentsId) {
        commentsRepository.deleteById(commentsId);
    }
}
