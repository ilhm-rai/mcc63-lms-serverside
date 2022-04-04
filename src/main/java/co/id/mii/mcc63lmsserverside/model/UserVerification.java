package co.id.mii.mcc63lmsserverside.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "UserVerification")
@Table(name = "user_verification")
@Data
@NoArgsConstructor
public class UserVerification {

  @Id
  @SequenceGenerator(
      name = "user_verification_sequence",
      sequenceName = "user_verification_sequence",
      allocationSize = 1)
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "user_verification_sequence")
  private Long id;

  @Column(nullable = false)
  private String token;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime expiresAt;

  private LocalDateTime confirmedAt;

  @ManyToOne
  @JoinColumn(
      nullable = false,
      name = "user_id")
  private User user;

  public UserVerification(String token, LocalDateTime createdAt, LocalDateTime expiresAt,
      User user) {
    this.token = token;
    this.createdAt = createdAt;
    this.expiresAt = expiresAt;
    this.user = user;
  }

}
