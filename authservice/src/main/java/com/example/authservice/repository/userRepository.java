package com.example.authservice.repository;

import com.example.authservice.entity.user;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<user,Long> {
}
