package co.id.mii.mcc63lmsserverside.model.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUser {

  private String fullName;
  private String email;
  private String password;
  private List<Long> rolesId;
}
