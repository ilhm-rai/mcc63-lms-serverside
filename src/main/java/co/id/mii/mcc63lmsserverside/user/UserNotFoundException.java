package co.id.mii.mcc63lmsserverside.user;

class UserNotFoundException extends RuntimeException {

  UserNotFoundException(Long id) {
    super("Cound not find user  " + id);
  }
}
