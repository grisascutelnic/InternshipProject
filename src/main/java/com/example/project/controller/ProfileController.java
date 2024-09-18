package com.example.project.controller;

import com.example.project.entity.Profile;
import com.example.project.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/profile")
@Controller
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{id}")
    public String showProfile(Model model,
                              @PathVariable Long id,
                              Authentication auth) {
        Profile profile = profileService.findById(id);
        if (profile == null) {
            model.addAttribute("error", "Profile not found for email: " + auth.getName());
            return "error"; // Point to an error page, or return the same profile page with an error message
        }
        model.addAttribute("profile", profile);
        model.addAttribute("profileId", id);
        return "profile";
    }


    @GetMapping("/editProfile")
    public String showEditContactForm(Model model, Authentication auth) {
        Profile profile = profileService.findByEmail(auth.getName());
        model.addAttribute("profile", profile);
        return "editProfile";
    }

    @PostMapping("/updateProfile")
    public String updateContact(@ModelAttribute("profile") Profile editedProfile,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                Authentication auth) {
        profileService.updateProfile(editedProfile, imageFile, auth);
        return "redirect:/profile/editProfile";
    }
}
