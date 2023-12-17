package com.example.BDForum.repository;

import com.example.BDForum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
