package co.id.mii.mcc63lmsserverside.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import co.id.mii.mcc63lmsserverside.model.UserVerification;
import co.id.mii.mcc63lmsserverside.repository.UserVerificationRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserVerificationService {

  private final UserVerificationRepository userVerificationRepository;

  public void saveUserVerification(UserVerification verification) {
    userVerificationRepository.save(verification);
  }

  public UserVerification getToken(String token) {
    return userVerificationRepository.findByToken(token)
        .orElseThrow(() -> new IllegalStateException("Could not found token"));
  }

  public void setConfirmedAt(String token) {
    UserVerification userVerification = getToken(token);
    userVerification.setConfirmedAt(LocalDateTime.now());
  }
}
