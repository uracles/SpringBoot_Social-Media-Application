package com.merakool.facebook.services.implementation;

import com.merakool.facebook.entities.AppUser;
import com.merakool.facebook.entities.Comment;
import com.merakool.facebook.entities.Post;
import com.merakool.facebook.repository.CommentRepository;
import com.merakool.facebook.repository.PostRepository;
import com.merakool.facebook.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public Comment createComment(Long postId, String commentText, AppUser user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = new Comment();
        comment.setText(commentText);
        comment.setTimestamp(LocalDateTime.now());
        comment.setUser(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
