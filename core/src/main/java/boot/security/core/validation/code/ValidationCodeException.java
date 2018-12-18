package boot.security.core.validation.code;

import org.springframework.security.core.AuthenticationException;

public class ValidationCodeException extends AuthenticationException {

  /**
   * Constructs an <code>AuthenticationServiceException</code> with the specified message.
   *
   * @param msg the detail message
   */
  public ValidationCodeException(String msg) {
    super(msg);
  }
}
