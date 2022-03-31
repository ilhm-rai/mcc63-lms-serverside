package co.id.mii.mcc63lmsserverside.service;

import co.id.mii.mcc63lmsserverside.model.Dto.UserDto;
import co.id.mii.mcc63lmsserverside.repository.UserRepository;
import co.id.mii.mcc63lmsserverside.model.User;
import co.id.mii.mcc63lmsserverside.exception.UserNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User addNewUser(UserDto userDto) {
        emailChecker(userDto.getEmail());
        User user = modelMapper.map(userDto, User.class);
        user.setIsActive(false);
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        getUserById(userId);
        userRepository.deleteById(userId);
    }

    public User updateUser(Long userId, UserDto userDto) {
        User user = getUserById(userId);

        if (userDto.getEmail() != null
                && userDto.getEmail().length() > 0
                && !Objects.equals(user.getEmail(), userDto.getEmail())) {
            emailChecker(userDto.getEmail());
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getPassword() != null
                && userDto.getPassword().length() > 0) {
            user.setPassword(userDto.getPassword());
        }

        return userRepository.save(user);
    }

    private void emailChecker(String email) {
        Optional<User> userOptional = userRepository
                .findUserByEmail(email);

        if (userOptional.isPresent()) {
            throw new IllegalStateException("Email is already registered");
        }
    }
}
