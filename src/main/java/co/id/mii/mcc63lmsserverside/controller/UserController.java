package co.id.mii.mcc63lmsserverside.controller;

import co.id.mii.mcc63lmsserverside.model.User;
import co.id.mii.mcc63lmsserverside.model.dto.request.ChangePassword;
import co.id.mii.mcc63lmsserverside.model.dto.request.CreateUser;
import co.id.mii.mcc63lmsserverside.model.dto.request.UpdateUser;
import co.id.mii.mcc63lmsserverside.model.dto.response.UserDataResponse;
import co.id.mii.mcc63lmsserverside.service.UserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  @ResponseBody
  public List<User> getUsers() {
    return userService.getUsers();
  }

  @GetMapping("{userId}")
  @ResponseBody
  public User getUserById(@PathVariable("userId") Long userId) {
    return userService.getUserById(userId);
  }

  @PostMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public UserDataResponse registerNewUser(@RequestBody CreateUser createUser) {
    return userService.addNewUser(createUser);
  }

  @DeleteMapping(path = "{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable("userId") Long userId) {
    userService.deleteUser(userId);
  }

  @PutMapping(path = "{userId}")
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public UserDataResponse updateUser(
      @PathVariable("userId") Long userId, @RequestBody UpdateUser updateUser) {

    return userService.updateUser(userId, updateUser);
  }

  @PutMapping(path = "{userId}/change-password")
  @ResponseStatus(HttpStatus.CREATED)
  public void changePassword(
      @PathVariable("userId") Long userId, @RequestBody ChangePassword changePassword) {

    userService.changePassword(userId, changePassword);
  }
}
