package com.example.BDForum.repository;

import com.example.BDForum.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommenstsRepository extends JpaRepository<Comments, Long> {
    void deleteByUserId(Long userId);
}
