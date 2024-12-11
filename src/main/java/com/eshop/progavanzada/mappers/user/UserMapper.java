package com.eshop.progavanzada.mappers.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.eshop.progavanzada.dtos.auth.UserDTO;
import com.eshop.progavanzada.models.User;

public class UserMapper {
  public static UserDTO toDTO(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setEmail(user.getUsername());
    userDTO.setRol(user.getRol());
    return userDTO;
  }

  public static User toModel(UserDTO userDTO) {
    User user = new User();
    user.setId(userDTO.getId());
    user.setUsername(userDTO.getEmail());
    user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
    user.setRol(userDTO.getRol());
    return user;
  }
}
