package com.howtodoinjava.app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

  @NotNull
  private Integer id;

  @NotBlank
  @Length(min = 2, max = 255)
  private String firstName;

  @NotBlank
  @Length(min = 2, max = 255)
  private String lastName;

  @Pattern(regexp = ".+@.+\\.[a-z]+")
  private String email;
}
