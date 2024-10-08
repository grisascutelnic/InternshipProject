package com.example.project.controller;

import com.example.project.entity.Announcement;
import com.example.project.entity.Event;
import com.example.project.entity.Profile;
import com.example.project.service.AnnouncementService;
import com.example.project.service.EventService;
import com.example.project.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;

@RequestMapping("/profile")
@Controller
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private EventService eventService;

    @GetMapping("/{id}")
    public String showProfile(Model model,
                              @PathVariable Long id,
                              Authentication auth,
                              Principal principal) {
        if (auth == null) {
            // Redirect or handle the unauthenticated user case
            return "redirect:/login"; // sau un mesaj de eroare
        }

        Profile profile = profileService.findById(id);
        if (profile == null) {
            model.addAttribute("error", "Profile not found for email: " + auth.getName());
            return "error"; // Point to an error page, or return the same profile page with an error message
        }

        List<Announcement> announcements = announcementService.findByProfileId(id);

        model.addAttribute("profile", profile);
        model.addAttribute("profileId", id);
        model.addAttribute("announcements", announcements);

        if (principal != null) {
            String authenticatedEmail = principal.getName();
            model.addAttribute("authenticatedEmail", authenticatedEmail);
        }
//        model.addAttribute("authenticatedEmail", auth.getName());
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

    @PostMapping("/rate/{id}")
    public String rateProfile(@PathVariable Long id, @RequestParam("rating") int rating, Authentication auth, Model model) {
        Profile profile = profileService.findById(id);
        if (profile == null) {
            return "redirect:/error";
        }
        DecimalFormat df = new DecimalFormat("#.#");
        String formattedRating = df.format(profile.getAverageRating());
        model.addAttribute("formattedRating", formattedRating);

        String userEmail = auth.getName(); // Obține email-ul utilizatorului autentic
        if (profile.hasRated(userEmail)) {
            model.addAttribute("error", "You have already rated this profile.");
            return "redirect:/profile/" + id;
        }

        // Actualizează totalul rating-urilor și numărul de rating-uri
        profile.setTotalRating(profile.getTotalRating() + rating);
        profile.setNumberOfRatings(profile.getNumberOfRatings() + 1);
        profile.addRater(userEmail);

        profileService.save(profile); // Salvează modificările

        return "redirect:/profile/" + id;
    }

    @GetMapping("/{id}/events")
    public String showCalendar(@PathVariable Long id, Model model, Principal principal) {
        Profile profile = profileService.findById(id);
        List<Event> events = eventService.getEventsForProfile(profile);
        model.addAttribute("profile", profile);
        model.addAttribute("events", events);

        if (principal != null) {
            String authenticatedEmail = principal.getName();
            model.addAttribute("authenticatedEmail", authenticatedEmail);
        }
        return "events";
    }


    @PostMapping("/{id}/events/add")
    public String addEvent(@PathVariable Long id, @ModelAttribute Event event) {
        Profile profile = profileService.findById(id);
        event.setProfile(profile);
        event.setStatus("Pending");
        eventService.addEvent(event);
        return "redirect:/profile/" + id + "/events";
    }

    @PostMapping("/{id}/events/{eventId}/updateStatus")
    public String updateEventStatus(@PathVariable Long id, @PathVariable Long eventId, @RequestParam String status) {
        eventService.updateEventStatus(eventId, status);
        return "redirect:/profile/" + id + "/events";
    }
}
