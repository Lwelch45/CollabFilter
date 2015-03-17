package models.exception;

/**
 * Created by laurence on 3/17/15.
 */
public class ItemNotFoundException extends Exception {
  public ItemNotFoundException() {
    
  }

  public ItemNotFoundException(String message) {
    super(message);
    
  }

  public ItemNotFoundException(Throwable cause) {
    super(cause);
    
  }

  public ItemNotFoundException(String message, Throwable cause) {
    super(message, cause);
    
  }
}
