package boot.security.browser.authentication;

import boot.security.core.properties.LoginType;
import boot.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component("authenticationSuccessHandler")
public class BrowserAuthenticationSuccessHandler
    extends SavedRequestAwareAuthenticationSuccessHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired private ObjectMapper objectMapper;

  @Autowired private SecurityProperties securityProperties;

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

    logger.info(
        "Authentication Success of LoginType " + securityProperties.getBrowser().getLoginType());
    if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(objectMapper.writeValueAsString(authentication));
    } else {
      super.onAuthenticationSuccess(request, response, authentication);
    }
  }
}
