package com.example.project.controller;

import com.example.project.entity.Feedback;
import com.example.project.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/list")
    public String listFeedbacks(Model model) {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        return "listFeedbacks";  // Pagina HTML pentru afișarea listei de feedback-uri
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
//redrictioneze pe pagina de feedback anunt
    @PostMapping("/saveFeedback")
    public String saveFeedback(@ModelAttribute("feedback") Feedback feedback) {
        feedbackService.saveFeedback(feedback);
        return "redirect:/announcements/viewAnnouncement";
    }

    @DeleteMapping("/deleteFeedback/{id}")
    public String deleteFeedback(@PathVariable("id") Long id) {
        feedbackService.deleteFeedback(id);
        return "redirect:/feedbacks/list";
    }
}
