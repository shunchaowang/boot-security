package boot.security.core.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CoreAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * Called when a user has been successfully authenticated.
   *
   * @param request the request which caused the successful authentication
   * @param response the response
   * @param authentication the <tt>Authentication</tt> object which was created during
   */
  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {

    ObjectMapper objectMapper = new ObjectMapper();

    logger.info("Authenticate success");

    // write authentication as a json by default
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(authentication));
  }
}
