package co.id.mii.mcc63lmsserverside.user;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "User")
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(name = "user_email_unique", columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "user_sequence")
  @Column(name = "id", updatable = false)
  private Long id;

  @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(255)")
  private String email;

  @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255)")
  private String password;

  @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT(1)")
  private Boolean isActive;

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
