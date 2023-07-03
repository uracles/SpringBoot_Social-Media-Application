package com.merakool.facebook.repository;


import com.merakool.facebook.entities.AppUser;
import com.merakool.facebook.entities.Like;
import com.merakool.facebook.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByPostAndUser(Post post, AppUser currentUsername);
    Integer deleteByPostAndUser(Post post, AppUser currentUsername);
}
