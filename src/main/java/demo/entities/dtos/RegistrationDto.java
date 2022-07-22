package demo.entities.dtos;

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
 
  @NonNull
  private String username;

  @NonNull
  private String email;

  @NonNull
  private String password;

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
