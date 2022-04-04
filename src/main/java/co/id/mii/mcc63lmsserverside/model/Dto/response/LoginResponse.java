package co.id.mii.mcc63lmsserverside.model.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String fullName;
    private String email;
    private List<String> authorities;
}
