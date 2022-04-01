package co.id.mii.mcc63lmsserverside.model.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class UserDataResponse {

  private Long id;
  private String fullName;
  private String email;
  private List<String> role;
}
