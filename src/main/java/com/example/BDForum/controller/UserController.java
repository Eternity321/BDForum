package com.example.BDForum.controller;

import com.example.BDForum.model.User;
import com.example.BDForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllUsers(Model model) {
        try {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            return "user_list";
        } catch (Exception e) {
            model.addAttribute("сообщение об ошибке", "Ошибка при получении пользователей.");
            return "error";
        }
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        try{
        model.addAttribute("user", new User());
        return "add_user";
        } catch (Exception e){
            throw new RuntimeException("Ошибка добавления пользователя", e);
        }
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        try {
            Optional<User> user = userService.getUserById(id);
            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                return "edit_user";
            } else {
                return "redirect:/users";
            }
        } catch (Exception e){
            model.addAttribute("сообщение об ошибке", "Ошибка при получении пользователей.");
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return "redirect:/users";
        } catch (Exception e){
            throw new RuntimeException("Ошибка удаления пользователя", e);
        }
    }
}
