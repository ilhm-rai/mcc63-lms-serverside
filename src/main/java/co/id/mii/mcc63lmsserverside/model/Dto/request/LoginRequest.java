package co.id.mii.mcc63lmsserverside.model.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

  private String email;
  private String password;
}
