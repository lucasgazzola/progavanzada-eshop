package com.eshop.progavanzada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.eshop.progavanzada.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
  UserDetails findByUsername(String username);
}