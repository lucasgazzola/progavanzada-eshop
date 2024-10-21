package com.eshop.progavanzada.constants;

import java.util.regex.Pattern;

import lombok.Data;

@Data
public class Consts {
  public static final int MAX_PASSWORD_LENGTH = 24;
  public static final int MIN_PASSWORD_LENGTH = 8;
  public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
  public static final Pattern EMAIL_REGEX_PATTERN = Pattern.compile(EMAIL_REGEX,
      Pattern.CASE_INSENSITIVE);
}
