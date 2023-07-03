package com.merakool.facebook.controller;

import com.merakool.facebook.entities.AppUser;
import com.merakool.facebook.entities.Comment;
import com.merakool.facebook.services.implementation.CommentServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentController {
    private final CommentServiceImpl commentServices;

    private Long postId;
    private String commentText;
    private HttpSession session;

    @PostMapping("/comments/create")
    public String createComment(@RequestParam("postId") Long postId, @RequestParam("commentText") String commentText, HttpSession session) {
        this.postId = postId;
        this.commentText = commentText;
        this.session = session;
        // Get the authenticated user from the session
        AppUser user = (AppUser) session.getAttribute("user");

        // Create the comment using the CommentService
        commentServices.createComment(postId, commentText, user);

        return "redirect:/home";
    }


    @GetMapping("/comments/{postId}")
    public String getCommentsByPostId(@PathVariable Long postId, Model model) {
        List<Comment> comments = commentServices.getCommentsByPostId(postId);
        model.addAttribute("comments", comments);
        return "comments";
    }
}
