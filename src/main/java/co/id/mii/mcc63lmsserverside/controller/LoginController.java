package co.id.mii.mcc63lmsserverside.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.id.mii.mcc63lmsserverside.model.dto.request.LoginRequest;
import co.id.mii.mcc63lmsserverside.model.dto.response.LoginResponse;
import co.id.mii.mcc63lmsserverside.service.LoginService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("login")
@AllArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @PostMapping
  public LoginResponse login(@RequestBody LoginRequest request) {
    return loginService.login(request);
  }
}
