package com.example.BDForum.controller;

import com.example.BDForum.model.Post;
import com.example.BDForum.service.PostService;
import com.example.BDForum.service.UserService;
import com.example.BDForum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService; // Ваш сервис пользователей

    @Autowired
    private CategoryService categoryService; // Ваш сервис категорий

    @GetMapping
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "post_list";
    }

    @GetMapping("/add")
    public String addPostForm(Model model) {
        model.addAttribute("post", new Post());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add_post";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("post") Post post) {
        postService.createPost(post);
        return "redirect:/posts";
    }

    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
        } else {
            return "redirect:/posts";
        }

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "edit_post";
    }

    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, @ModelAttribute("post") Post post) {
        postService.updatePost(id, post);
        return "redirect:/posts";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
