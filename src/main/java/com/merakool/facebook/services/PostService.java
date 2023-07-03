package com.merakool.facebook.services;

import com.merakool.facebook.entities.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> getAllPosts();

    Optional<Post> getPostById(Long id);

    Post createPost(Post post);

    Post updatePost(Post post);

    void deletePost(Long id);
}
