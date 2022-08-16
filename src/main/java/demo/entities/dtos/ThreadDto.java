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
public class ThreadDto {

  // Used for creating new threads.

  @NonNull
  private String subject;

  @NonNull
  private String content;

  @NonNull
  private Integer categoryId;

  @NonNull
  private Integer userId;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    String newline = System.getProperty("line.separator");

    builder.append(this.getClass().getName() + " {" + newline);
    builder.append(" subject: " + subject + newline);
    builder.append(" content: " + content + newline);
    builder.append(" categoryId: " + categoryId + newline);
    builder.append(" userId: " + userId + newline);
    builder.append("}");

    return builder.toString();
  }
}
