package boot.security.browser.authentication;

import boot.security.core.properties.LoginType;
import boot.security.core.properties.SecurityProperties;
import boot.security.core.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component("authenticationnticationFailureHandler ")
public class BrowserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private ObjectMapper objectMapper;

  @Autowired private SecurityProperties securityProperties;

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

    logger.info(
        "Authentication Failure of LoginType " + securityProperties.getBrowser().getLoginType());

    if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      response.setContentType("application/json;charset=UTF-8");
      response
          .getWriter()
          .write(objectMapper.writeValueAsString(new SimpleResponse<>(exception.getMessage())));
    } else {
      super.onAuthenticationFailure(request, response, exception);
    }
  }
}
