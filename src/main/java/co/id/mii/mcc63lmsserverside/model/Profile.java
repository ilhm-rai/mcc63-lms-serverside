package co.id.mii.mcc63lmsserverside.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Profile")
@Table(name = "profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

  @Id
  private Long id;

  @Column(name = "full_name", nullable = false, columnDefinition = "VARCHAR(255)")
  private String fullName;

  @Column(name = "phone_number", columnDefinition = "VARCHAR(20)")
  private String phoneNumber;

  @Column(name = "bio", columnDefinition = "TEXT")
  private String bio;

  @Column(name = "organization", columnDefinition = "VARCHAR(255)")
  private String organization;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToOne(cascade = CascadeType.ALL)
  @MapsId
  @JoinColumn(name = "id")
  private User user;

  public Profile(String fullName) {
    this.fullName = fullName;
  }
}