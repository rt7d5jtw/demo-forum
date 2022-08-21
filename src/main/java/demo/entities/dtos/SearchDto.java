package demo.entities.dtos;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class SearchDto {

  @NotEmpty(message = "Keywords may not be empty.")
  @NonNull
  private String keywords;

  @NonNull
  private String author;

  @NonNull
  private String category;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    String newline = System.getProperty("line.separator");

    builder.append(this.getClass().getName() + " {" + newline);
    builder.append(" keywords: " + keywords + newline);
    builder.append(" author: " + author + newline);
    builder.append(" category: " + category + newline);
    builder.append("}");

    return builder.toString();
  }
}
