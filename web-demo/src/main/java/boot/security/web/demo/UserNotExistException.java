package boot.security.web.demo;

public class UserNotExistException extends RuntimeException {

  private String id;

  /**
   * Constructs a new runtime exception with {@code null} as its detail message. The cause is not
   * initialized, and may subsequently be initialized by a call to {@link #initCause}.
   */
  public UserNotExistException(String id) {
    super("runtime exception user not exist ");
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
