package models.exception;

/**
 * Created by laurence on 3/17/15.
 */
public class ItemNotFoundException extends RuntimeException {
  public ItemNotFoundException() {

  }

  public ItemNotFoundException(String message) {
    super(message);

  }
  
  public ItemNotFoundException(Integer id) {
    super("Item: " + id + " not found");
  }

  public ItemNotFoundException(Throwable cause) {
    super(cause);

  }

  public ItemNotFoundException(String message, Throwable cause) {
    super(message, cause);

  }
}
