package com.merakool.facebook.controller;

import com.merakool.facebook.entities.AppUser;
import com.merakool.facebook.entities.Post;
import com.merakool.facebook.repository.AppUserRepository;
import com.merakool.facebook.services.implementation.AppUserServiceImpl;
import com.merakool.facebook.services.implementation.PostServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class AppUserController {
    private final AppUserServiceImpl userServices;
    private final AppUserRepository userRepository;
    private final PostServiceImpl postServices;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("FacebookUser", new AppUser());
        return "registration";
    }


    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user")AppUser user) {
        try {
            userServices.registerUser(user);
            return "redirect:/welcome";
        } catch (IllegalArgumentException e) {
            return "registration";
        }
    }


    @GetMapping("/welcome")
    public String showWelcomePage() {
        return "welcome";
    }


    @PostMapping("/welcome")
    public String processLogin(@RequestParam("email") String email,
                               @RequestParam("password") String password,
                               HttpSession session,
                               Model model) {
        // Attempt to log the user in
        Optional<AppUser> optionalUser = userServices.loginUser(email, password);

        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();

            // Set the user in the session for authentication
            session.setAttribute("user", user);

            // Redirect to the home page
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "welcome";
        }
    }


    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        // Get the authenticated user from the session
        AppUser user = (AppUser) session.getAttribute("user");

        // Check if the user is logged in
        if (user != null) {
            // Add the user's name to the model
            model.addAttribute("userName", user.getName());

            // Retrieve all posts
            List<Post> posts = postServices.getAllPosts();

            // Add the posts to the model
            model.addAttribute("posts", posts);

            // Return the homepage template
            return "home";
        } else {
            // If the user is not logged in, redirect to the login page
            return "redirect:/welcome";
        }
    }
}
