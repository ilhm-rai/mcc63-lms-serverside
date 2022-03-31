package co.id.mii.mcc63lmsserverside.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(Long id) {
    super("Cound not find user  " + id);
  }
}
