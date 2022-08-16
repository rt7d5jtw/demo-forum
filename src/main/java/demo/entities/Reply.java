package demo.entities;

import java.sql.Timestamp;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "replies")
@Entity(name = "reply")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Reply extends AbstractEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  @NonNull
  @Column(name = "content", nullable = true, updatable = true)
  private String content;

  @NonNull
  @Column(name = "created", nullable = false, updatable = false)
  private Date created;

  @NonNull
  @Column(name = "updated", nullable = true, updatable = true)
  private Timestamp updated;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    String newline = System.getProperty("line.separator");

    builder.append(this.getClass().getName() + " {" + newline);
    builder.append(" userId: " + user + newline);
    builder.append(" postId: " + post + newline);
    builder.append(" content: " + content + newline);
    builder.append(" created: " + created + newline);
    builder.append(" updated: " + updated + newline);
    builder.append("}");

    return builder.toString();
  }
}
