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
public class UserDto {

  // Used for signing in.

  @NonNull
  private String email;

  @NonNull
  private String username;

  @NonNull
  private String password;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    String newline = System.getProperty("line.separator");

    builder.append(this.getClass().getName() + " {" + newline);
    builder.append(" email: " + email + newline);
    builder.append(" email: " + username + newline);
    builder.append(" password: " + password + newline);
    builder.append("}");

    return builder.toString();
  }
}
