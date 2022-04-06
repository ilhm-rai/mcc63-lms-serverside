package co.id.mii.mcc63lmsserverside.model;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

  @Id
  private Long id;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToOne(cascade = CascadeType.ALL)
  @MapsId
  @JoinColumn(
      nullable = false,
      name = "enrollment_id")
  private Enrollment enrollment;

  @Column(nullable = false)
  private String paymentSlip;

  @Column(nullable = false)
  private LocalDateTime uploadedAt;

  private LocalDateTime confirmedAt;

  public Payment(String paymentSlip, Enrollment enrollment, LocalDateTime uploadedAt) {
    this.paymentSlip = paymentSlip;
    this.enrollment = enrollment;
    this.uploadedAt = uploadedAt;
  }
}
