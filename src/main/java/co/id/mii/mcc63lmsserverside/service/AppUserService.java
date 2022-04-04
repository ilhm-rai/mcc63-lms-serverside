package co.id.mii.mcc63lmsserverside.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import co.id.mii.mcc63lmsserverside.model.AppUser;
import co.id.mii.mcc63lmsserverside.model.User;
import co.id.mii.mcc63lmsserverside.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

  private final static String USER_NOT_FOUND_MSG = "Could not find user with email %s";
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findUserByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    return new AppUser(user);
  }

}
