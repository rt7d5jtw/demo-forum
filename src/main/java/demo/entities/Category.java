package demo.entities;

import java.sql.Timestamp;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

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
@NamedQueries({
  @NamedQuery(
    name = "Category.findAll", 
    query = "from category"
  ),
  @NamedQuery(
    name = "Category.findById",
    query = "from category u where u.id = :id"
  ),
  @NamedQuery(
    name = "Category.findByTopic",
    query = "from category u where u.topic= :topic"
  )
})
public class Category extends AbstractEntity {

  @NonNull
  @Column(name = "topic", length = 100, nullable = false)
  private String topic;

  @NonNull
  @Column(name = "topic_description", length = 255, nullable = false)
  private String topicDescription;

  @NonNull
  @Column(name = "amount_threads", nullable = false, updatable = true)
  private Integer amountThreads;

  @NonNull
  @Column(name = "amount_posts", nullable = false, updatable = true)
  private Integer amountPosts;

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
    builder.append(" topic: " + topic + newline);
    builder.append(" topicDescription: " + topicDescription + newline);
    builder.append(" amountThreads: " + amountThreads + newline);
    builder.append(" amountPosts: " + amountPosts + newline);
    builder.append(" created: " + created + newline);
    builder.append(" updated: " + updated + newline);
    builder.append("}");

    return builder.toString();
  }
}
