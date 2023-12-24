package com.example.BDForum.controller;

import com.example.BDForum.model.Comments;
import com.example.BDForum.repository.PostNotFoundException;
import com.example.BDForum.service.CommentsService;
import com.example.BDForum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;
    @Autowired
    private PostService postService;

    @GetMapping
    public String getAllComments(Model model) {
        List<Comments> comments = commentsService.getAllComments();
        model.addAttribute("comments", comments);
        return "comment_list";
    }

    @PostMapping("/add")
    public String addComment(@RequestParam Long postId, @RequestParam String text, RedirectAttributes redirectAttributes) {
        try {
            commentsService.addComment(postId, text);
            redirectAttributes.addAttribute("id", postId);
            return "redirect:/posts/{id}";
        } catch (Exception e) {
            return "error";
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody Comments comments) {
        commentsService.updateComment(id, comments);
        return ResponseEntity.ok("Комментарий успешно обновлен");
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id, @RequestParam(name = "postId", required = false) Long postId, Model model) {
        try {
            if (postId != null) {
                commentsService.deleteComment(id);
                return "redirect:/posts/" + postId;
            } else {
                return "redirect:/comments";
            }
        } catch (PostNotFoundException e) {
            model.addAttribute("сообщение об ошибке", e.getMessage());
            return "error";
        } catch (Exception e) {
            model.addAttribute("сообщение об ошибке", "Ошибка удаления комментария");
            return "error";
        }
    }


}
