package co.id.mii.mcc63lmsserverside.user;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Autowired
  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public User getUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
  }

  public UserDto addNewUser(UserDto userDto) {
    emailChecker(userDto.getEmail());
    User user = userMapper.userDTOToUser(userDto);
    user.setIsActive(false);
    return userMapper.userToUserDTO(userRepository.save(user));
  }

  public void deleteUser(Long userId) {
    getUserById(userId);
    userRepository.deleteById(userId);
  }

  public UserDto updateUser(Long userId, UserDto userDto) {
    User user = getUserById(userId);

    if (userDto.getEmail() != null &&
        userDto.getEmail().length() > 0 &&
        !Objects.equals(user.getEmail(), userDto.getEmail())) {
      emailChecker(userDto.getEmail());
      user.setEmail(userDto.getEmail());
    }

    if (userDto.getPassword() != null &&
        userDto.getPassword().length() > 0) {
      user.setPassword(userDto.getPassword());
    }

    return userMapper.userToUserDTO(userRepository.save(user));
  }

  private void emailChecker(String email) {
    Optional<User> userOptional = userRepository
        .findUserByEmail(email);

    if (userOptional.isPresent()) {
      throw new IllegalStateException("Email is already registered");
    }
  }
}
