package demo.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// Posts refer to independent pieces of writing that refer to threads.
@Table(name = "posts")
@Entity(name = "post")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Post extends AbstractEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "thread_id")
  private Thread thread;

  @NonNull
  @Column(name = "content")
  private String content;

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
    builder.append(" userId: " + user + newline);
    builder.append(" threadId: " + thread + newline);
    builder.append(" content: " + content + newline);
    builder.append(" created: " + created + newline);
    builder.append(" updated: " + updated + newline);
    builder.append("}");

    return builder.toString();
  }

}
