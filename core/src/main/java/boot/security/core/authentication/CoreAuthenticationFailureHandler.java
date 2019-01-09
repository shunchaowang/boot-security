package boot.security.core.authentication;

import boot.security.core.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CoreAuthenticationFailureHandler implements AuthenticationFailureHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * Called when an authentication attempt fails.
   *
   * @param request the request during which the authentication attempt occurred.
   * @param response the response.
   * @param exception the exception which was thrown to reject the authentication
   */
  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {

    logger.info("Authentication Failure");

    ObjectMapper objectMapper = new ObjectMapper();

    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setContentType("application/json;charset=UTF-8");
    response
        .getWriter()
        .write(objectMapper.writeValueAsString(new SimpleResponse<>(exception.getMessage())));
  }
}
