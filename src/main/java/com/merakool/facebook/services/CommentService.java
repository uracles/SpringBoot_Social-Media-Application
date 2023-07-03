package com.merakool.facebook.services;

import com.merakool.facebook.entities.AppUser;
import com.merakool.facebook.entities.Comment;

import java.util.List;

public interface CommentService {
    Comment createComment(Long postId, String commentText, AppUser user);
    List<Comment> getCommentsByPostId(Long postId);
}
