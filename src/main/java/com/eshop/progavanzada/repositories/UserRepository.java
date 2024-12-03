package com.eshop.progavanzada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eshop.progavanzada.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUsername(String username);
}