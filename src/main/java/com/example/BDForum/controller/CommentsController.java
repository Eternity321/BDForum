package com.example.BDForum.controller;

import com.example.BDForum.model.Comments;
import com.example.BDForum.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping
    public String getAllComments(Model model) {
        List<Comments> comments = commentsService.getAllComments();
        model.addAttribute("comments", comments);
        return "comment_list";
    }

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody Comments comments) {
        commentsService.createComment(comments);
        return ResponseEntity.ok("Comment created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody Comments comments) {
        commentsService.updateComment(id, comments);
        return ResponseEntity.ok("Comment updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentsService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
