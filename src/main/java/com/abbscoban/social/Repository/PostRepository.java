package com.abbscoban.social.Repository;

import com.abbscoban.social.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository  extends JpaRepository<Post,Long> {

    List<Post> findByUserId(Long userId);


    List<Post> findAllByOrderByCreateTimeDesc();

    List<Post> findAllByUserIdOrderByCreateTimeDesc(Long userId);

    List<Post> findAllByUserIdOrderByCreateTimeAsc(Long userId);
}
