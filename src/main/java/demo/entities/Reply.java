package demo.entities;

import java.sql.Timestamp;
import java.sql.Date;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

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
@NamedQueries({
  @NamedQuery(
    name = "Reply.findAll", 
    query = "from reply"
  ),
  @NamedQuery(
    name = "Reply.findById",
    query = "from reply u where u.id = :id"
  ),
  @NamedQuery(
    name = "Reply.findRelated",
    query = "from reply u where u.thread = :thread"
  )
})
public class Reply extends AbstractEntity {

  @NonNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "thread_id")
  private Thread thread;

  @NonNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

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
    builder.append(" threadId: " + thread + newline);
    builder.append(" userId: " + user + newline);
    builder.append(" content: " + content + newline);
    builder.append(" created: " + created + newline);
    builder.append(" updated: " + updated + newline);
    builder.append("}");

    return builder.toString();
  }
}
