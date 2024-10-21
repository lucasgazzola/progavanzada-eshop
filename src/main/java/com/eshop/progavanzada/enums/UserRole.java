package com.eshop.progavanzada.enums;

public enum UserRole {
  ADMIN("ADMIN"),
  USER("USER");

  private String role;

  UserRole(String role) {
    this.role = role;
  }

  public String getValue() {
    return role;
  }
}
