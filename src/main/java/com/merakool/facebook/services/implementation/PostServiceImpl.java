package com.merakool.facebook.services.implementation;

import com.merakool.facebook.entities.Post;
import com.merakool.facebook.repository.AppUserRepository;
import com.merakool.facebook.repository.PostRepository;
import com.merakool.facebook.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final AppUserRepository userRepository;


    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        Optional<Post> postToUpdate = postRepository.findById(post.getId());
        if (postToUpdate.isPresent()) {
            postToUpdate.get().setContent(post.getContent());
            postRepository.save(postToUpdate.get());
        }
        return post;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
