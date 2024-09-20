package com.example.project.controller;

import com.example.project.entity.Announcement;
import com.example.project.entity.Feedback;
import com.example.project.entity.Profile;
import com.example.project.service.AnnouncementService;
import com.example.project.service.FeedbackService;
import com.example.project.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private ProfileService profileService;

//    @GetMapping("/list")
//    public String listFeedbacks(Model model) {
//        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
//        model.addAttribute("feedbacks", feedbacks);
//        return "viewAnnouncement";  // Pagina HTML pentru afișarea listei de feedback-uri
//    }

    @GetMapping("/list")
    public String listFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        return "viewAnnouncement";  // Pagina HTML pentru afișarea listei de feedback-uri
    }

    @GetMapping("/createFeedback")
    public String showNewFeedbackForm(Model model) {
        model.addAttribute("feedback", new Feedback());
        return "createFeedback";  // Formular HTML pentru adăugarea unui feedback nou
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("feedback", new Feedback());
        return "viewAnnouncement";
    }

    @PostMapping("/saveFeedback")
    public String saveFeedback(@ModelAttribute("feedback") Feedback feedback,
                               @RequestParam("profileId") Long profileId,
                               @RequestParam("announcementId") Long announcementId) {

        Profile profile = profileService.findProfileById(profileId);

        if (profile != null) {
            // Setează profilul în feedback
            feedback.setProfile(profile);

            // Salvează feedback-ul
            feedbackService.saveFeedback(feedback);
        }

        return "redirect:/announcements/viewAnnouncement/" + announcementId;
    }

}
