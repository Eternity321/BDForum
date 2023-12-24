package com.example.BDForum.repository;

import com.example.BDForum.model.Comments;
import com.example.BDForum.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommenstsRepository extends JpaRepository<Comments, Long> {
    void deleteByPost(Post post);
    void deleteById(Long commentId);

}
