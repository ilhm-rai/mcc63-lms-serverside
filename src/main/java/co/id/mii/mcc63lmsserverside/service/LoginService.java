package co.id.mii.mcc63lmsserverside.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import co.id.mii.mcc63lmsserverside.model.User;
import co.id.mii.mcc63lmsserverside.model.dto.request.LoginRequest;
import co.id.mii.mcc63lmsserverside.model.dto.response.LoginResponse;
import co.id.mii.mcc63lmsserverside.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginService {

  private final AuthenticationManager authenticationManager;
  private final AppUserService appUserService;
  private final UserRepository userRepository;

  public LoginResponse login(LoginRequest request) {
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
    Authentication auth = authenticationManager.authenticate(authToken);
    SecurityContext sc = SecurityContextHolder.getContext();
    sc.setAuthentication(auth);

    UserDetails userDetails = appUserService.loadUserByUsername(request.getEmail());

    List<String> authorities = userDetails.getAuthorities()
        .stream()
        .map(authority -> authority.getAuthority())
        .collect(Collectors.toList());

    User user = userRepository.findUserByEmail(request.getEmail()).get();

    return new LoginResponse(user.getProfile().getFullName(), user.getEmail(), authorities);
  }

}
