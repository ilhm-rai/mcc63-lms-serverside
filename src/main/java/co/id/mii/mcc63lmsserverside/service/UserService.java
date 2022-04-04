package co.id.mii.mcc63lmsserverside.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import co.id.mii.mcc63lmsserverside.exception.UserNotFoundException;
import co.id.mii.mcc63lmsserverside.model.Profile;
import co.id.mii.mcc63lmsserverside.model.User;
import co.id.mii.mcc63lmsserverside.model.UserVerification;
import co.id.mii.mcc63lmsserverside.model.dto.request.ChangePassword;
import co.id.mii.mcc63lmsserverside.model.dto.request.CreateUser;
import co.id.mii.mcc63lmsserverside.model.dto.request.UpdateUser;
import co.id.mii.mcc63lmsserverside.model.dto.response.UserDataResponse;
import co.id.mii.mcc63lmsserverside.repository.UserRepository;
import co.id.mii.mcc63lmsserverside.util.EmailSender;
import co.id.mii.mcc63lmsserverside.util.EmailValidator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

  private final static String USER_NOT_FOUND_MSG = "Could not find user with email %s";
  private final ModelMapper modelMapper;
  private final EmailValidator emailValidator;
  private final EmailSender emailSender;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserRepository userRepository;
  private final UserVerificationService userVerificationService;

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public User getUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));
  }

  public String createUser(CreateUser createUser) {
    emailChecker(createUser.getEmail());

    User user = modelMapper.map(createUser, User.class);
    Profile profile = user.getProfile();
    profile.setUser(user);

    String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());

    user.setPassword(encodePassword);

    userRepository.save(user);

    String token = UUID.randomUUID().toString();

    UserVerification userVerification = new UserVerification(
        token,
        LocalDateTime.now(),
        LocalDateTime.now().plusMinutes(15),
        user);

    userVerificationService.saveUserVerification(userVerification);

    String link = "http://localhost:8080/api/registration/verify?token=" + token;

    emailSender.send(createUser.getEmail(), buildEmail(createUser.getFullName(), link));

    return token;
  }

  public void enableUser(String email) {
    User user = userRepository.findUserByEmail(email)
        .orElseThrow(() -> new IllegalStateException(String.format(USER_NOT_FOUND_MSG, email)));
    user.setIsEnabled(true);
  }

  @Transactional
  public String verifyAccount(String token) {
    UserVerification userVerification = userVerificationService.getToken(token);

    if (userVerification.getConfirmedAt() != null) {
      throw new IllegalStateException("Email is already verified");
    }

    LocalDateTime expiresAt = userVerification.getExpiresAt();

    if (expiresAt.isBefore(LocalDateTime.now())) {
      throw new IllegalStateException("Token is expired");
    }

    userVerificationService.setConfirmedAt(token);
    enableUser(userVerification.getUser().getEmail());

    return "Your email successfully verified. Thanks!";
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
      user.setPassword(bCryptPasswordEncoder.encode(password));
    }

    userRepository.save(user);
  }

  private void emailChecker(String email) {
    boolean isValidEmail = emailValidator.test(email);

    if (!isValidEmail) {
      throw new IllegalStateException("Email is invalid");
    }

    boolean userExists = userRepository.findUserByEmail(email).isPresent();

    if (userExists) {
      throw new IllegalStateException("Email is already registered");
    }
  }

  private String buildEmail(String name, String link) {
    return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n"
        +
        "\n" +
        "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
        "\n" +
        "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
        +
        "    <tbody><tr>\n" +
        "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
        "        \n" +
        "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
        +
        "          <tbody><tr>\n" +
        "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
        "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
        +
        "                  <tbody><tr>\n" +
        "                    <td style=\"padding-left:10px\">\n" +
        "                  \n" +
        "                    </td>\n" +
        "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
        +
        "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Verify your email</span>\n"
        +
        "                    </td>\n" +
        "                  </tr>\n" +
        "                </tbody></table>\n" +
        "              </a>\n" +
        "            </td>\n" +
        "          </tr>\n" +
        "        </tbody></table>\n" +
        "        \n" +
        "      </td>\n" +
        "    </tr>\n" +
        "  </tbody></table>\n" +
        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
        +
        "    <tbody><tr>\n" +
        "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
        "      <td>\n" +
        "        \n" +
        "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
        +
        "                  <tbody><tr>\n" +
        "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
        "                  </tr>\n" +
        "                </tbody></table>\n" +
        "        \n" +
        "      </td>\n" +
        "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
        "    </tr>\n" +
        "  </tbody></table>\n" +
        "\n" +
        "\n" +
        "\n" +
        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
        +
        "    <tbody><tr>\n" +
        "      <td height=\"30\"><br></td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
        "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
        +
        "        \n" +
        "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi "
        + name
        + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\""
        + link
        + "\" target=\"_blink\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>"
        +
        "        \n" +
        "      </td>\n" +
        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "      <td height=\"30\"><br></td>\n" +
        "    </tr>\n" +
        "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
        "\n" +
        "</div></div>";
  }
}
