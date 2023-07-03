package com.merakool.facebook.controller;

import com.merakool.facebook.entities.AppUser;
import com.merakool.facebook.entities.Post;
import com.merakool.facebook.services.implementation.PostServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostServiceImpl postService;

    @GetMapping("/posts")
    public String getAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "posts";
    }


    @GetMapping("/{id}")
    public String getPostById(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        model.addAttribute("post", post.orElse(null));
        return "post";
    }

    @GetMapping("/posts/create")
    public String showCreatePostForm(Model model) {
        System.out.println("Bros do well oo");
        model.addAttribute("post", new Post());
        System.out.println("Omo this one mad");
        return "create-post";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute("post") Post post, @RequestParam("content") String content, HttpSession session) {
        // Get the authenticated user from the session
        System.out.println("oh mehn!");
        AppUser user = (AppUser) session.getAttribute("user");

        // Create a new Post object
//        Post post = new Post();
        post.setUser(user);
        post.setContent(content);
        post.setTimestamp(LocalDateTime.now());

        // Save the post using the PostService
        postService.createPost(post);
        System.out.println("Chai!, there's God oo");

        return "redirect:/home";
    }


    @GetMapping("/posts/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        model.addAttribute("post", post.orElseThrow());
        return "edit-post";
    }

    @PostMapping("/posts/{id}/edit")
    public String updatePost(@PathVariable Long id, @ModelAttribute("post") Post post) {
        post.setId(id);

        System.out.println("POST"+post);
        postService.updatePost(post);
        return "home";
    }


    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Post> optionalPost = postService.getPostById(id);

        if (optionalPost.isPresent()) {
            postService.deletePost(id);
        } else {
            redirectAttributes.addFlashAttribute("error", "The post does not exist.");
        }

        return "redirect:/posts";
    }
}
