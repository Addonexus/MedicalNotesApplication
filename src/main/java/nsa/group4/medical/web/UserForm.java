package nsa.group4.medical.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserForm {


//  @NotNull
//  @NotEmpty
//  private String firstName;
//
//  @NotNull
//  @NotEmpty
//  private String surnameName;

  @NotNull
  @NotEmpty
  @Size(min=5, max=30, message="wahoo")
  private String username;

  @NotNull
  @NotEmpty
  @Size(min=5, max=30, message="wahoo")
  private String password;



}
