package com.eshop.progavanzada.services.auth;

import com.eshop.progavanzada.dtos.auth.CreateUserDTO;
import com.eshop.progavanzada.dtos.auth.JwtDTO;
import com.eshop.progavanzada.dtos.auth.SignInDTO;
import com.eshop.progavanzada.dtos.auth.UpdateUserDTO;
import com.eshop.progavanzada.dtos.auth.UserDTO;

import jakarta.servlet.http.HttpServletResponse;

public interface IAuthServices {
  public UserDTO signUp(CreateUserDTO data);

  public JwtDTO signIn(SignInDTO data, HttpServletResponse response);

  // public void signOut(HttpServletResponse response);

  public UserDTO updateUser(UpdateUserDTO data);
}
