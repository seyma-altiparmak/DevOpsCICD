package org.example.controller;

import org.example.model.User;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String viewHomePage(Model model){
        model.addAttribute("listUser", userService.getAllUsers());
        return "html/index";
    }

    @GetMapping("/add")
    public String showNewUserForm(Model model){
        model.addAttribute("user",new User());
        return "html/new_user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user")User user){
        userService.saveUsers(user);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model){
        User user = userService.getUserById(id);
        model.addAttribute("user",user);
        return "html/update_user";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id){
        this.userService.deleteUserById(id);
        return "redirect:/";
    }
}
