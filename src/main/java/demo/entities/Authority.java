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
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Table(name = "authorities")
@Entity(name = "authority")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@NamedQueries({
  @NamedQuery(
    name = "Authority.findAll", 
    query = "from authority"
  ),
  @NamedQuery(
    name = "Authority.findByEmail",
    query = "from authority u where u.email = :email"
  ),
  @NamedQuery(
    name = "Authority.findByAuth",
    query = "from authority u where u.authority = :authority"
  )
})
public class Authority extends AbstractEntity {

  @NonNull
  @Column(name = "email", length = 50, nullable = false)
  private String email;

  @NonNull
  @Column(name = "authority", length = 50, nullable = true)
  private String authority;

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    String newline = System.getProperty("line.separator");

    builder.append(this.getClass().getName() + " {" + newline);
    builder.append(" email: " + email + newline);
    builder.append(" authority: " + authority + newline);
    builder.append("}");

    return builder.toString();
  }
}
