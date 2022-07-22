package demo.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

// Category represents a division of conversations topics.
@Table(name = "categories")
@Entity(name = "category")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Category extends AbstractEntity {

  @NonNull
  @Column(name = "topic", length = 100, nullable = false)
  private String topic;

  @NonNull
  @Column(name = "description", nullable = true)
  private String description;

  @NonNull
  @Column(name = "created", nullable = false, updatable = false)
  private Timestamp created;

  @NonNull
  @Column(name = "updated", nullable = true, updatable = true)
  private Timestamp updated;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    String newline = System.getProperty("line.separator");

    builder.append(this.getClass().getName() + " {" + newline);
    builder.append(" topic: " + topic + newline);
    builder.append(" description: " + description + newline);
    builder.append(" created: " + created + newline);
    builder.append(" updated: " + updated + newline);
    builder.append("}");

    return builder.toString();
  }
}
