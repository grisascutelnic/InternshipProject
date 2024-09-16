package com.example.project.controller;

import com.example.project.entity.Profile;
import com.example.project.service.ProfileService;
import com.example.project.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/profile")
@Controller
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showProfile(Model model) {
        model.addAttribute("profile", new Profile());
        return "profile";
    }


    @GetMapping("/editProfile")
    public String showEditProfile() {
        return "editProfile";
    }
}
