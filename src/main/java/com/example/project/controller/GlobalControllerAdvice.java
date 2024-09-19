package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.security.core.Authentication;
import com.example.project.service.ProfileService;
import com.example.project.entity.Profile;

/*
Acesta a fost creat pentru a putea accesa profilul cu id-ul meu (la gmail-ul care sunt conectat) de pe orice pagina
 */
@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private ProfileService profileService;

    @ModelAttribute
    public void addProfileId(WebRequest request, Authentication auth, Model model) {
        if (auth != null) {
            String email = auth.getName();
            Profile profile = profileService.findByEmail(email);
            if (profile != null) {
                model.addAttribute("profileId", profile.getId());
            }
        }
    }
}
