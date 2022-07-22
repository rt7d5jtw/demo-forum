package demo.entities.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class RegistrationDto {

  // Used for registration.
 
  @NotEmpty(message = "Username may not be empty.")
  @Size(min = 3, max = 32, message = "Username must be between 3 and 32 characters long.")
  @NonNull
  private String username;

  @NotEmpty(message = "Email may not be empty.")
  @Size(min = 3, max = 320, message = "Email must be between 3 and 320 characters long.")
  @Email
  @NonNull
  private String email;

  @NotEmpty(message = "Password may not be empty.")
  @NonNull
  private String password;

  @NotEmpty(message = "Repeat password may not be empty.")
  @NonNull
  private String password_verify;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    String newline = System.getProperty("line.separator");

    builder.append(this.getClass().getName() + " {" + newline);
    builder.append(" username: " + username + newline);
    builder.append(" email: " + email + newline);
    builder.append(" password: " + password + newline);
    builder.append(" password_verify: " + password_verify + newline);
    builder.append("}");

    return builder.toString();
  }
}
