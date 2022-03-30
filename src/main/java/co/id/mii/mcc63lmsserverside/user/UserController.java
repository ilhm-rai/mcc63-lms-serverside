package co.id.mii.mcc63lmsserverside.user;

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
@RequestMapping(path = "user")
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
  public UserDto registerNewUser(@RequestBody UserDto userDto) {
    return userService.addNewUser(userDto);
  }

  @DeleteMapping(path = "{userId}")
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping(path = "{userId}")
  public void deleteUser(@PathVariable("userId") Long userId) {
    userService.deleteUser(userId);
  }

  @PutMapping(path = "{userId}")
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto updateUser(
      @PathVariable("userId") Long userId, @RequestBody UserDto userDto) {

    return userService.updateUser(userId, userDto);
  }
}
