package com.merakool.facebook.services;

import jakarta.servlet.http.HttpServletRequest;

public interface LikeService {
    void toggleLike(Long postId, HttpServletRequest request);
    void deleteLike(Long likeId);
}
