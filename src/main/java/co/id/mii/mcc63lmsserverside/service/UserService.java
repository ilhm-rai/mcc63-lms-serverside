package co.id.mii.mcc63lmsserverside.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.id.mii.mcc63lmsserverside.exception.UserNotFoundException;
import co.id.mii.mcc63lmsserverside.model.Profile;
import co.id.mii.mcc63lmsserverside.model.Role;
import co.id.mii.mcc63lmsserverside.model.User;
import co.id.mii.mcc63lmsserverside.model.dto.request.CreateUser;
import co.id.mii.mcc63lmsserverside.model.dto.request.ChangePassword;
import co.id.mii.mcc63lmsserverside.model.dto.request.UpdateUser;
import co.id.mii.mcc63lmsserverside.model.dto.response.UserDataResponse;
import co.id.mii.mcc63lmsserverside.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final RoleService roleService;

  @Autowired
  public UserService(
      UserRepository userRepository,
      ModelMapper modelMapper,
      RoleService roleService) {
    this.userRepository = userRepository;
    this.roleService = roleService;
    this.modelMapper = modelMapper;
    this.modelMapper.addMappings(newUserDataMap);
    this.modelMapper.addMappings(newUserDataResponseMap);
  }

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public User getUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
  }

  public UserDataResponse addNewUser(CreateUser createUser) {
    emailChecker(createUser.getEmail());

    User user = modelMapper.map(createUser, User.class);
    Profile profile = user.getProfile();
    profile.setUser(user);

    user.setIsActive(false);
    return modelMapper.map(userRepository.save(user), UserDataResponse.class);
  }

  public void deleteUser(Long userId) {
    getUserById(userId);
    userRepository.deleteById(userId);
  }

  public UserDataResponse updateUser(Long userId, UpdateUser updateUser) {
    User user = getUserById(userId);

    String email = updateUser.getEmail();
    String fullName = updateUser.getFullName();

    if (email != null &&
        email.length() > 0 &&
        !Objects.equals(user.getEmail(), email)) {
      emailChecker(email);
      user.setEmail(email);
    }

    if (fullName != null &&
        fullName.length() > 0 &&
        !Objects.equals(user.getProfile().getFullName(), fullName)) {
      user.getProfile().setFullName(fullName);
    }

    return modelMapper.map(userRepository.save(user), UserDataResponse.class);
  }

  public void changePassword(Long userId, ChangePassword updateUser) {
    User user = getUserById(userId);
    String password = updateUser.getNewPassword();

    if (password != null &&
        password.length() > 0) {
      user.setPassword(password);
    }

    userRepository.save(user);
  }

  private void emailChecker(String email) {
    Optional<User> userOptional = userRepository
        .findUserByEmail(email);

    if (userOptional.isPresent()) {
      throw new IllegalStateException("Email is already registered");
    }
  }

  Converter<List<Long>, List<Role>> convertToRole = new Converter<List<Long>, List<Role>>() {
    public List<Role> convert(MappingContext<List<Long>, List<Role>> context) {
      return context.getSource()
          .stream()
          .map(id -> roleService.getById(id))
          .collect(Collectors.toList());
    }
  };

  Converter<List<Role>, List<String>> convertToRoleName = new Converter<List<Role>, List<String>>() {
    public List<String> convert(MappingContext<List<Role>, List<String>> context) {
      return context.getSource().stream()
          .map(role -> role.getName())
          .collect(Collectors.toList());
    }
  };

  PropertyMap<CreateUser, User> newUserDataMap = new PropertyMap<CreateUser, User>() {
    @Override
    protected void configure() {
      map().getProfile().setFullName(source.getFullName());
      using(convertToRole).map(source.getRolesId()).setRoles(new ArrayList<>());
    }
  };

  PropertyMap<User, UserDataResponse> newUserDataResponseMap = new PropertyMap<User, UserDataResponse>() {
    @Override
    protected void configure() {
      map().setFullName(source.getProfile().getFullName());
      using(convertToRoleName).map(source.getRoles()).setRole(new ArrayList<>());
    }
  };
}
