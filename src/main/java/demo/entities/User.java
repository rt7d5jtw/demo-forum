package demo.entities;

import demo.entities.enums.Role;
import demo.entities.enums.UserStatus;

import java.sql.Timestamp;
import java.sql.Date;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/* Avoid @Data for JPA's 
 * Always cascade from Parent-side to Child-side,
 * so no CascadeType.* for @ManyToOne since entity
 * state transitions should be propagated from
 * parent-side entities to child-side ones.
 *
 * Set mappedBy and orphanRemoval on the parent-side,
 * and use lazy fetching on both sides of the
 * association. 
 *
 * To conform to Hibernate docs, if entities are
 * stored in a Set or are reattached to a new 
 * Persistence Context, then we should override
 * equals() and hashCode(). This is done to respect
 * the consistency of entity equality across all its
 * state transitions. */

@Table(name = "users")
@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@NamedQueries({
  @NamedQuery(
    name = "User.findAll", 
    query = "from user"
  ),
  @NamedQuery(
    name = "User.findById",
    query = "from user u where u.id = :id"
  ),
  @NamedQuery(
    name = "User.findByEmail",
    query = "from user u where u.email = :email"
  )
})
public class User extends AbstractEntity {

  // https://www.rfc-editor.org/errata_search.php?rfc=3696&eid=1690
  @NonNull
  @NaturalId
  @Column(name = "email", unique = true, nullable = false, length = 254)
  private String email;

  @NonNull
  @Column(name = "username", unique = true, length = 254, nullable = false)
  private String username;

  @NonNull
  @Column(name = "password", length = 255, nullable = false)
  private String password;

  @NonNull
  @Column(name = "role", nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private Role role;

  @NonNull
  @Column(name = "user_status", nullable = false, length = 40)
  @Enumerated(EnumType.STRING)
  private UserStatus userstatus;

  @NonNull
  @Column(name = "created", nullable = false, updatable = false)
  private Date created;

  @Column(name = "updated", nullable = true, updatable = true)
  private Timestamp updated;

  @Column(name = "lastloggedin", nullable = true, updatable = true)
  private Timestamp lastloggedin;

  @Column(name = "version", nullable = true, updatable = true)
  private int version; // 0 by default

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    String newline = System.getProperty("line.separator");

    builder.append(this.getClass().getName() + " {" + newline);
    builder.append(" email: " + email + newline);
    builder.append(" password: " + password + newline);
    builder.append(" role: " + role + newline);
    builder.append(" userstatus: " + userstatus + newline);
    builder.append(" created: " + created + newline);
    builder.append(" updated: " + updated + newline);
    builder.append(" lastloggedin: " + lastloggedin + newline);
    builder.append(" version: " + version + newline);
    builder.append("}");

    return builder.toString();
  }
}
