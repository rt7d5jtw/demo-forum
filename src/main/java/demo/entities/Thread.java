package demo.entities;

import java.sql.Timestamp;
import java.sql.Date;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.FetchType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

// Threads refer to single points of conversation (topic) in a category.
@Table(name = "threads")
@Entity(name = "thread")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@NamedQueries({
  @NamedQuery(
    name = "Thread.findAll", 
    query = "from thread"
  ),
  @NamedQuery(
    name = "Thread.findById",
    query = "from thread u where u.id = :id"
  ),
  @NamedQuery(
    name = "Thread.findRelated",
    query = "from thread u where u.category = :category"
  )
})
public class Thread extends AbstractEntity {

  @NonNull
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @NonNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @NonNull
  @Column(name = "subject", length = 100, nullable = false)
  private String subject;

  @NonNull
  @Column(name = "content")
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
    builder.append(" categoryId: " + category + newline);
    builder.append(" userId: " + user + newline);
    builder.append(" subject: " + subject + newline);
    builder.append(" content: " + content + newline);
    builder.append(" created: " + created + newline);
    builder.append(" updated: " + updated + newline);
    builder.append("}");


    return builder.toString();
  }
}
