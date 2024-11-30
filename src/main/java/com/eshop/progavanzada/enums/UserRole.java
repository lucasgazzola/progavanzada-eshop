package com.eshop.progavanzada.enums;

public enum UserRole {
  ADMIN("ADMIN"),
  USER("USER"),
  AUDITOR("AUDITOR");

  private String role;

  UserRole(String role) {
    this.role = role;
  }

  public String getValue() {
    return role;
  }
}
