package oef1.fullproject.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MyController {

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }
        model.addAttribute("username", userDetails.getUsername());
        return "home"; // Returns the home.html template
    }
    
    @GetMapping("/login")
    public String login(Model model) {
        return "login"; // Return "login" without ".html"
    }
    @GetMapping("/logout")
    public String login() {
        return "redirect:/login"; // Return "login" without ".html"
    }
    @RequestMapping("/")
    public String index() {
        return "redirect:/login"; // Redirect to login page from root
    }
}

