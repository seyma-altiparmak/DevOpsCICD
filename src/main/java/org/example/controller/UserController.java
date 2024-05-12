package org.example.controller;

import org.example.model.User;
import org.example.s3.services.S3Service;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UserController {
    private final S3Service s3Service;
    private UserService userService;


    @Autowired
    public UserController(S3Service s3Service,UserService userService) {
        this.s3Service = s3Service;
        this.userService = userService;
    }

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
    public String saveUser(@ModelAttribute User user, @RequestParam("image") MultipartFile image) {
        String fileName = null;
        try {
            fileName = s3Service.uploadFile(image);
            user.setImageName(fileName);
            user.setImageLink(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
