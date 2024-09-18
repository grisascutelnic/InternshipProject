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


    @GetMapping("/editProfile/{id}")
    public String showEditProfileForm(@PathVariable Long id, Model model, Authentication auth) {
        Profile profile = profileService.findById(id);
        if (profile == null || !profile.getEmail().equals(auth.getName())) {
            // În cazul în care profilul nu există sau utilizatorul nu are permisiunea de a edita acest profil
            return "redirect:/error";
        }
        model.addAttribute("profile", profile);
        return "editProfile";
    }


    @PostMapping("/updateProfile/{id}")
    public String updateContact(@PathVariable Long id,
                                @ModelAttribute("profile") Profile editedProfile,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                Authentication auth) {
        if (editedProfile == null) {
            // În cazul în care profilul nu există sau utilizatorul nu are permisiunea de a actualiza acest profil
            return "redirect:/error";
        }
        profileService.updateProfile(id, editedProfile, imageFile, auth);
        return "redirect:/profile/" + id;
    }
}
