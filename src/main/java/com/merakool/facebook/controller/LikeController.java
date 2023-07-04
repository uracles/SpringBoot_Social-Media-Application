package com.merakool.facebook.controller;

import com.merakool.facebook.services.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class LikeController {
    private final LikeService likesService;

    @PostMapping("/increment/{postId}")
    public String incrementLikeCount(@PathVariable("postId") Long postId, HttpServletRequest request) {
        likesService.toggleLike(postId, request);
        return "redirect:/home";
    }


    @DeleteMapping("/{likeId}")
    public String deleteLike(@PathVariable("likeId") Long likeId) {
        likesService.deleteLike(likeId);
        return "redirect:/home";
    }
}
