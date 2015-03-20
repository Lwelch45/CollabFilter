package models.exception;

/**
 * Created by laurence on 3/17/15.
 */
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {

  }

  public UserNotFoundException(String message) {
    super(message);

  }

  public UserNotFoundException(Integer id) {
    super("User: " + id + " not found");
  }

  public UserNotFoundException(Throwable cause) {
    super(cause);

  }

  public UserNotFoundException(String message, Throwable cause) {
    super(message, cause);

  }
}
