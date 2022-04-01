package co.id.mii.mcc63lmsserverside.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileData {

  private String fullName;
  private String phoneNumber;
  private String bio;
  private String organization;

}
