package com.example.BDForum.controller;

import com.example.BDForum.model.Post;
import com.example.BDForum.repository.PostNotFoundException;
import com.example.BDForum.service.PostService;
import com.example.BDForum.service.UserService;
import com.example.BDForum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public String getAllPosts(
            @RequestParam(name = "sortMethod", defaultValue = "id") String sortMethod,
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model) {
        try {
            Page<Post> posts;
            Pageable pageable = PageRequest.of(page, 10, Sort.by(sortMethod));
            if (username != null && !username.isEmpty()) {
                posts = postService.getAllPostsByUsername(username, pageable);
            } else {
                posts = postService.getAllPosts(pageable);
            }
            long totalPosts = postService.getTotalPosts();
            model.addAttribute("posts", posts.getContent());
            model.addAttribute("totalPosts", totalPosts);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", posts.getTotalPages());

            return "post_list";
        } catch (Exception e) {
            model.addAttribute("сообщение об ошибке", "Ошибка при получении постов.");
            return "error";
        }
    }


    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        try {
            Optional<Post> postOptional = postService.getPostById(id);
            if (postOptional.isPresent()) {
                Post post = postOptional.get();
                model.addAttribute("post", post);
                return "view-post";
            } else {
                throw new PostNotFoundException("Пост с идентификатором " + id + " не найден");
            }
        } catch (Exception e) {
            model.addAttribute("сообщение об ошибке", "Ошибка при получении поста.");
            return "error";
        }
    }


    @GetMapping("/add")
    public String addPostForm(Model model) {
        try {
        model.addAttribute("post", new Post());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add_post";
    } catch (Exception e) {
        model.addAttribute("сообщение об ошибке", "Ошибка при добавлении поста.");
        return "error";
        }
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("post") Post post) {
        postService.createPost(post);
        return "redirect:/posts/list";

    }

    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        try {

            Optional<Post> post = postService.getPostById(id);
            if (post.isPresent()) {
                model.addAttribute("post", post.get());
            } else {
                return "redirect:/posts/list";
            }

            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("categories", categoryService.getAllCategories());

            return "edit_post";
        } catch (Exception e) {
            model.addAttribute("сообщение об ошибке", "Ошибка при изменении поста.");
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, @ModelAttribute("post") Post post) {
        postService.updatePost(id, post);
        return "redirect:/posts/list";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id, Model model) {
        try {
            postService.deletePost(id);
            return "redirect:/posts/list";
        } catch (PostNotFoundException e) {
            model.addAttribute("сообщение об ошибке", e.getMessage());
            return "error";
        } catch (Exception e) {
            model.addAttribute("сообщение об ошибке", "Ошибка удаления поста");
            return "error";
        }
    }

}
