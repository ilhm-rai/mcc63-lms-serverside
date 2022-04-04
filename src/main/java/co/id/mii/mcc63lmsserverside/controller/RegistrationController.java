package co.id.mii.mcc63lmsserverside.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.id.mii.mcc63lmsserverside.model.dto.request.CreateUser;
import co.id.mii.mcc63lmsserverside.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("registration")
@AllArgsConstructor
public class RegistrationController {

  private UserService userService;

  @PostMapping
  public String register(@RequestBody CreateUser createUser) {
    return userService.createUser(createUser);
  }

  @GetMapping(path = "verify")
  public String verifyAccount(@RequestParam("token") String token) {
    return userService.verifyAccount(token);
  }

}
