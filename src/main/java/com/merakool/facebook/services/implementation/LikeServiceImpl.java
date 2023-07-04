package com.merakool.facebook.services.implementation;

import com.merakool.facebook.entities.AppUser;
import com.merakool.facebook.entities.Like;
import com.merakool.facebook.entities.Post;
import com.merakool.facebook.exceptions.NotFoundException;
import com.merakool.facebook.repository.LikeRepository;
import com.merakool.facebook.repository.PostRepository;
import com.merakool.facebook.services.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likesRepository;
    private final PostRepository postRepository;


    @Override
    public void toggleLike(Long postId, HttpServletRequest request) {
        // Get the current user from the session
        HttpSession session = request.getSession();
        AppUser currentUser = (AppUser)  session.getAttribute("user");
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // Check if the user has already liked the post
            boolean hasLiked = likesRepository.existsByPostAndUser(post, currentUser);

            if (hasLiked) {
                // User has already liked the post, so decrement the like count and delete the like
                post.setLikeCount(post.getLikeCount() - 1);
                likesRepository.deleteByPostAndUser(post, currentUser);
            } else {
                // User hasn't liked the post, so increment the like count and save a new like
                post.setLikeCount(post.getLikeCount() + 1);
                Like like = new Like();
                like.setPost(post);
                like.setUser(currentUser);
                likesRepository.save(like);
            }

            postRepository.save(post);
        } else {
            throw new NotFoundException("Post not found with ID: " + postId);
        }
    }

    @Override
    public void deleteLike(Long likeId) {
        Optional<Like> optionalLike = likesRepository.findById(likeId);
        if (optionalLike.isPresent()) {
            Like like = optionalLike.get();
            likesRepository.delete(like);

            // Update the like count in the associated post
            Post post = like.getPost();
            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);
        } else {
            throw new NotFoundException("Like not found with ID: " + likeId);
        }
    }
}
