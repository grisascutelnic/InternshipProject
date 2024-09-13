package com.example.project.controller;

import com.example.project.dto.UserDto;
import com.example.project.entity.User;
import com.example.project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {

        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {

        User existingUser = userService.findByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null, "There is already an account registered with this email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "redirect:/register?error";
        }
        userService.saveUser(userDto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {

        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "login";
    }

    @PostMapping("/login/verification")
    public String loginVerification(@Valid @ModelAttribute("user") UserDto userDto) {

        User existingUser = userService.findByEmail(userDto.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (existingUser != null && passwordEncoder.matches(userDto.getPassword(), existingUser.getPassword())) {
            return "redirect:/index";
        } else {
            return "redirect:/login?error";
        }
    }
}
