package com.example.project.controller;

import com.example.project.entity.Profile;
import com.example.project.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginSuccessController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/loginSuccess")
    public String loginSuccess(Authentication auth, RedirectAttributes redirectAttributes) {
        if (auth == null) {
            // Dacă autentificarea este null, trimitem utilizatorul înapoi la pagina de login
            redirectAttributes.addFlashAttribute("error", "Autentificare eșuată. Vă rugăm să încercați din nou.");
            return "redirect:/login";
        }

        String email = auth.getName();
        Profile profile;

        try {
            profile = profileService.findByEmail(email);
            if (profile == null) {
                // Dacă profilul nu a fost găsit, aruncăm o excepție sau gestionăm eroarea
                redirectAttributes.addFlashAttribute("error", "Profilul nu a fost găsit.");
                return "redirect:/login";
            }
        } catch (Exception e) {
            // Gestionăm orice altă excepție neașteptată
            redirectAttributes.addFlashAttribute("error", "A apărut o eroare la recuperarea profilului.");
            return "redirect:/login";
        }

        Long profileId = profile.getId();
        return "redirect:/profile/" + profileId;
    }

}
